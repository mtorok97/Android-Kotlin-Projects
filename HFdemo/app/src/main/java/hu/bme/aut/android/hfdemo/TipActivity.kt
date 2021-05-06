package hu.bme.aut.android.hfdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.hfdemo.adapter.MatchAdapter
import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.data.Match
import hu.bme.aut.android.hfdemo.data.Tip
import hu.bme.aut.android.hfdemo.databinding.ActivityTipBinding
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import hu.bme.aut.android.hfdemo.extensions.RetrofitResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


class TipActivity : BaseActivity(), MatchAdapter.MatchItemClickListener {
    private lateinit var binding: ActivityTipBinding
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var retrofitResult: RetrofitResult
    private var sportRes: AllData? = null
    private var currentDate: String = getDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofitResult = RetrofitResult()
        matchAdapter = MatchAdapter(this)
        matchAdapter.itemClickListener = this
        //simpleItemRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.match_tip_list).adapter = matchAdapter

        getData()
        binding.btn1.setOnClickListener {
            getretrofit()
        }
    }

    fun getretrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-football-beta.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            //.client(okHttpClient) //Ez csak akkor kell ha a headeröket itt adjuk hozzá, ha egységes headerök akkor hasznos
            .build()

        val resultsAPI = retrofit.create(ResultsAPI::class.java)

        //binding.btnGetResults.setOnClickListener {

        val resultsCall = resultsAPI.getMatch("140", getSeason(), currentDate)

        resultsCall.enqueue(object : Callback<AllData> {
            override fun onFailure(call: Call<AllData>, t: Throwable) {
                //binding.tvData.text = t.message
            }

            override fun onResponse(
                call: Call<AllData>,
                response: Response<AllData>
            ) {
                var sportResult = response.body()
                if (sportResult != null) {
                    sportRes = sportResult
                }
                //sportRes = RetrofitResult.getretrofit()

                MatchAdapter.matchList = emptyList()
                //megpróbálom a recycle viewba tenni az adatot az api hívás után
                if (sportResult?.response != null) {
                    for (element in sportResult.response!!) {
                        //dátum kiszedése
                        val matchDate = "${
                            (element.fixture!!.date?.take(13)?.takeLast(2)?.toInt()
                                ?.plus(2)).toString()
                        }${(element.fixture.date?.take(16)?.takeLast(3))}"
                        //eddig dátum
                        matchAdapter.addMatch(
                            element.fixture.id?.let { it1 ->
                                element.teams?.home?.name?.let { it2 ->
                                    element.teams.away?.name?.let { it3 ->
                                        Match(
                                            it1,
                                            it2,
                                            it3,
                                            element.goals!!.home, element.goals.away,
                                            element.teams.home.logo, element.teams.away.logo,
                                            matchDate
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
            }
        })
    }

    //private fun validateForm() = binding.etTitle.validateNonEmpty() && binding.etBody.validateNonEmpty()

    private fun uploadTip() {
        val newTip = Tip(uid, userName, "290", "away")

        val db = Firebase.firestore

        db.collection("tips")
            .document("Barca")
            .set(newTip)
            .addOnSuccessListener {
                toast("Tip created")
                //finish()
            }
            .addOnFailureListener { e -> toast(e.toString()) }
    }

    private fun getData() {
        val db = Firebase.firestore
        val docRef = db.collection("tips").document("U7AeQhWcoaMr537PTvcr")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    override fun onItemClick(match: Match) {
        val tempMatch = arrayOf<String>(
            match.matchId.toString(),
            match.homeTeam,
            match.awayTeam,
            match.homeGoals.toString(),
            match.awayGoals.toString(),
            match.homeImageURL.toString(),
            match.awayImageURL.toString(),
            match.matchDate.toString(),
        )
        val intent = Intent(this, DetailTipActivity::class.java)
        intent.putExtra("MATCH", tempMatch)
        startActivity(intent)
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
}