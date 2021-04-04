package hu.bme.aut.android.hfdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.databinding.FragmentResultsBinding
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultsBinding.inflate(layoutInflater, container, false)

        //FONTOS KÓD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //így is lehet headert hozzáadni, ezzel az öszes hívás headerje ez lesz
        /*val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("x-rapidapi-key", "c7546e1840mshbbd50343a36cd3bp1cd6a8jsnebbc644c0c12")
                    builder.header("x-rapidapi-host", "api-football-beta.p.rapidapi.com")
                    builder.header("Accept-Encoding", "identity")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()*/

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-football-beta.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            //.client(okHttpClient) //Ez csak akkor kell ha a headeröket itt adjuk hozzá, ha egységes headerök akkor hasznos
            .build()


        val resultsAPI = retrofit.create(ResultsAPI::class.java)

        binding.btnGetResults.setOnClickListener {

            val resultsCall = resultsAPI.getMoney("140", "2020", "2021-04-03")

            resultsCall.enqueue(object : Callback<AllData> {
                override fun onFailure(call: Call<AllData>, t: Throwable) {
                    binding.tvData.text = t.message
                }

                override fun onResponse(
                    call: Call<AllData>,
                    response: Response<AllData>
                ) {
                    val sportResult = response.body()
                    val homeTeam = sportResult?.response?.get(0)?.teams?.home?.name
                    val awayTeam = sportResult?.response?.get(0)?.teams?.away?.name
                    val homeGoals = sportResult?.response?.get(0)?.goals?.home
                    val awayGoals = sportResult?.response?.get(0)?.goals?.away

                    val homeTeam1 = sportResult?.response?.get(1)?.teams?.home?.name
                    val awayTeam1 = sportResult?.response?.get(1)?.teams?.away?.name
                    val homeGoals1 = sportResult?.response?.get(1)?.goals?.home
                    val awayGoals1 = sportResult?.response?.get(1)?.goals?.away

                    val homeTeam2 = sportResult?.response?.get(2)?.teams?.home?.name
                    val awayTeam2 = sportResult?.response?.get(2)?.teams?.away?.name
                    val homeGoals2 = sportResult?.response?.get(2)?.goals?.home
                    val awayGoals2 = sportResult?.response?.get(2)?.goals?.away

                    binding.tvData.text =
                        "$homeTeam ${homeGoals.toString()} - ${awayGoals.toString()} $awayTeam\r\n" +
                                "$homeTeam1 ${homeGoals1.toString()} - ${awayGoals1.toString()} $awayTeam1\r\n" +
                                "$homeTeam2 ${homeGoals2.toString()} - ${awayGoals2.toString()} $awayTeam2"
                }
            })
        }
        return binding.root
    }
}
