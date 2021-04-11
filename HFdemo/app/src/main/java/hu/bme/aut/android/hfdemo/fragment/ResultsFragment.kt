package hu.bme.aut.android.hfdemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.MatchAdapter
import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.data.Match
import hu.bme.aut.android.hfdemo.data.Response_http
import hu.bme.aut.android.hfdemo.databinding.FragmentResultsBinding
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Calendar

class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var sportRes: AllData

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecycleView() {
        val demoData = mutableListOf(
            Match(1, "Real Betis", "Atletico Madrid", 3, 1, "https://media.api-sports.io/football/teams/543.png", "https://media.api-sports.io/football/teams/530.png", "21:00"),
            Match(1, "Real Betis", "Atletico Madrid", 3, 1, "https://media.api-sports.io/football/teams/543.png", "https://media.api-sports.io/football/teams/530.png", "21:00"),
            Match(1, "Real Betis", "Atletico Madrid", 3, 1, "https://media.api-sports.io/football/teams/543.png", "https://media.api-sports.io/football/teams/530.png", "21:00")
        )
        matchAdapter = this.context?.let { MatchAdapter(it) }!!
        //matchAdapter.itemClickListener = this
        //matchAdapter.addMatch(demoData[0])
        //matchAdapter.addMatch(demoData[1])
        //matchAdapter.addMatch(demoData[2])
        binding.root.findViewById<RecyclerView>(R.id.match_list).adapter = matchAdapter
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultsBinding.inflate(layoutInflater, container, false)
        setupRecycleView()
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

            val resultsCall = resultsAPI.getMoney("140", getSeason(), getDate())

            resultsCall.enqueue(object : Callback<AllData> {
                override fun onFailure(call: Call<AllData>, t: Throwable) {
                    binding.tvData.text = t.message
                }

                override fun onResponse(
                        call: Call<AllData>,
                        response: Response<AllData>
                ) {
                    //Adatok mentése
                    var homeTeamList = mutableListOf<String>()
                    var awayTeamList = mutableListOf<String>()
                    var homeGoalsList = mutableListOf<Int?>()
                    var awayGoalsList = mutableListOf<Int?>()
                    //homeTeamList.add("hallo")


                    val sportResult = response.body()
                    if (sportResult != null) {
                        sportRes = sportResult
                    }
                    if (sportResult?.response != null) {
                        for (element in sportResult.response) {
                            element.teams?.home?.name?.let { it1 -> homeTeamList.add(it1); print("jall") }
                            element.teams?.away?.name?.let { it2 -> awayTeamList.add(it2) }
                            element.goals!!.home.let { it3 -> homeGoalsList.add(it3) }
                            element.goals!!.away.let { it4 -> awayGoalsList.add(it4) }
                            //homeGoalsList.add(element.goals!!.home)
                            //homeGoalsList.add(null)

                        }
                    }
                    //megpróbálom a recycle viewba tenni az adatot az api hívás után
                    if (sportResult?.response != null) {
                        for (element in sportResult.response) {
                            //dátum kiszedése
                            val matchDate = "${(element.fixture!!.date?.take(13)?.takeLast(2)?.toInt()
                                ?.plus(2)).toString()}${(element.fixture!!.date?.take(16)?.takeLast(3))}"
                                //eddig dátum
                            matchAdapter.addMatch(
                                element.fixture?.id?.let { it1 -> element.teams?.home?.name?.let { it2 ->
                                    element.teams.away?.name?.let { it3 ->
                                        Match(it1,
                                            it2,
                                            it3,
                                            element.goals!!.home, element.goals!!.away,
                                            element.teams.home.logo, element.teams.away.logo,
                                            matchDate)
                                    }
                                } },
                            )
                        }
                    }

                    //val homeTeam = sportResult?.response?.get(0)?.teams?.home?.name

                    /*binding.tvData.text =
                            "$homeTeam ${homeGoals.toString()} - ${awayGoals.toString()} $awayTeam\r\n" +
                                    "$homeTeam1 ${homeGoals1.toString()} - ${awayGoals1.toString()} $awayTeam1\r\n" +
                                    "$homeTeam2 ${homeGoals2.toString()} - ${awayGoals2.toString()} $awayTeam2\n" +
                                    "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 2} ${Calendar.getInstance().get(Calendar.MINUTE)} ${Calendar.getInstance().get(Calendar.SECOND)}"

                     */
                    //binding.tvData.text = "${homeTeamList.get(3)} ${homeGoalsList?.get(3)?.toString() ?: ""} - ${awayGoalsList?.get(3)?.toString() ?: ""} ${awayTeamList[3]}\r\n"
                }
            })
        }
        return binding.root
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
