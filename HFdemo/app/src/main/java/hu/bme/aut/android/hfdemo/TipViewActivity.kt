package hu.bme.aut.android.hfdemo

import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.data.Points
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.ActivityTipViewBinding
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class TipViewActivity : BaseActivity(), OnDataLoaded {
    private lateinit var binding: ActivityTipViewBinding
    private var currentDate: String = getDate()
    var ondata: OnDataLoaded? = null
    private var ids = Array(10, { it * 0 })
    private var homegoals: Array<Int?> =
        arrayOf(null, null, null, null, null, null, null, null, null, null)
    private var awaygoals: Array<Int?> =
        arrayOf(null, null, null, null, null, null, null, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ondata = this
        checkResults(ondata!!)
    }

    fun checkResults(onDataLoaded: OnDataLoaded) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-football-beta.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val resultsAPI = retrofit.create(ResultsAPI::class.java)

        val resultsCall = resultsAPI.getMatch("140", getSeason(), currentDate)

        resultsCall.enqueue(object : Callback<AllData> {
            override fun onFailure(call: Call<AllData>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<AllData>,
                response: Response<AllData>
            ) {
                val sportResult = response.body()
                onDataLoaded.onDataLoaded(sportResult)
            }
        })
    }

    private fun uploadPoints(my_tip: Tip) {
        val db = Firebase.firestore
        db.collection("spanish_tips")
            .document("$userName-${getDate()}-${my_tip.matchID}")
            .set(my_tip)
            .addOnSuccessListener {
                //pontok sikeresen hozzáadva
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun calculatePoints() {
        val db = Firebase.firestore
        for ((index, element) in ids.withIndex()) {
            val docRef =
                db.collection("spanish_tips").document("$userName-${getDate()}-${element}")
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if (homegoals[index] != null && awaygoals[index] != null) {
                            val my_tip = document.toObject<Tip>()
                            if (my_tip?.tip == "HOME" && homegoals[index]!! > awaygoals[index]!!) {
                                my_tip.points = 1
                                uploadPoints(my_tip)
                            } else if (my_tip?.tip == "AWAY" && homegoals[index]!! < awaygoals[index]!!) {
                                my_tip.points = 1
                                uploadPoints(my_tip)
                            } else if (my_tip?.tip == "DRAW" && homegoals[index] == awaygoals[index]) {
                                my_tip.points = 1
                                uploadPoints(my_tip)
                            } else {
                                my_tip?.points = 0
                                if (my_tip != null) {
                                    uploadPoints(my_tip)
                                }
                            }
                        }
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
        }
    }

    private fun getSeason(): String {
        //Júliusig az előző 2020-as szezont jelzi ki, augusztól már az új szezon meccseit
        return if (Calendar.getInstance().get(Calendar.MONTH) + 1 <= 7) {
            "${Calendar.getInstance().get(Calendar.YEAR) - 1}"
        } else {
            "${Calendar.getInstance().get(Calendar.YEAR)}"
        }
    }

    private fun getDate(): String {
        val currentDate = Calendar.getInstance()
        val currentYear = "${currentDate.get(Calendar.YEAR)}"
        //Month (Format matters because of API)
        val currentMonthInt = currentDate.get(Calendar.MONTH) + 1
        val currentMonth: String = if (currentMonthInt < 10) {
            "0$currentMonthInt"
        } else {
            "$currentMonthInt"
        }
        //Day (Format matters because of API)
        val currentDayInt = currentDate.get(Calendar.DAY_OF_MONTH)
        val currentDay: String = if (currentDayInt < 10) {
            "0$currentDayInt"
        } else {
            "$currentDayInt"
        }
        return "$currentYear-$currentMonth-$currentDay"
    }

    override fun onDataLoaded(datas: AllData?) {
        if (datas?.response != null) {
            var i = 0
            for (element in datas.response) {
                ids[i] = datas.response.get(i).fixture?.id!!
                homegoals[i] = element.goals?.home
                awaygoals[i] = element.goals?.away
                i++
            }
        }
        calculatePoints()
    }
}

interface OnDataLoaded {
    fun onDataLoaded(datas: AllData?)
}