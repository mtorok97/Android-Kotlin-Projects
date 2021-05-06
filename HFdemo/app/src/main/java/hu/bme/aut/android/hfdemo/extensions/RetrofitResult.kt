package hu.bme.aut.android.hfdemo.extensions

import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class RetrofitResult {

    companion object {
        private var currentDate: String = getDate()
        fun getretrofit(): AllData? {
            var sportRes: AllData? = null
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
                    val sportResult = response.body()
                    sportRes = response.body()
                }
            })
            return sportRes
        }

        fun getSeason(): String {
            //Júliusig az előző 2020-as szezont jelzi ki, augusztól már az új szezon meccseit
            return if (Calendar.getInstance().get(Calendar.MONTH) + 1 <= 7) {
                "${Calendar.getInstance().get(Calendar.YEAR) - 1}"
            } else {
                "${Calendar.getInstance().get(Calendar.YEAR)}"
            }
        }

        fun getDate(): String {
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

    /*fun getResult(): AllData {
        var currentDate: String = getDate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-football-beta.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            //.client(okHttpClient) //Ez csak akkor kell ha a headeröket itt adjuk hozzá, ha egységes headerök akkor hasznos
            .build()

        val resultsAPI = retrofit.create(ResultsAPI::class.java)

        val resultsCall = resultsAPI.getMatch("140", getSeason(), currentDate)

        resultsCall.enqueue(
            object : Callback<AllData> {
                override fun onFailure(call: Call<AllData>, t: Throwable) {
                    //binding.tvData.text = t.message
                    t.message?.let { Log.d("HIBAAA", it) }
                }

                override fun onResponse(
                    call: Call<AllData>,
                    response: Response<AllData>
                ) {
                    sportRes = response.body()!!
                    val sportResult = response.body()
                    if (sportResult != null) {
                        sportRes = sportResult
                    }
                }
            })

        return sportRes
    }*/


}


