package hu.bme.aut.android.hfdemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.MatchAdapter
import hu.bme.aut.android.hfdemo.data.AllData
import hu.bme.aut.android.hfdemo.data.Match
import hu.bme.aut.android.hfdemo.databinding.FragmentResultsBinding
import hu.bme.aut.android.hfdemo.network.ResultsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


class ResultsFragment : Fragment(), DatePickerDialogFragment.DateListener{

    private lateinit var binding: FragmentResultsBinding
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var sportRes: AllData
    private var currentDate: String = getDate()

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecycleView() {
        matchAdapter = this.context?.let { MatchAdapter(it) }!!
        //matchAdapter.itemClickListener = this
        binding.root.findViewById<RecyclerView>(R.id.match_list).adapter = matchAdapter
    }

    //Options menü beállítása a következő függvényekkel
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_date -> {
                showDatePickerDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialogFragment()
        datePicker.setTargetFragment(this, 0)
        datePicker.show(getParentFragmentManager(), DatePickerDialogFragment.TAG)
    }

    override fun onDateSelected(date: String) {
        currentDate = date
        binding.tvData.text = "Date: $currentDate"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultsBinding.inflate(layoutInflater, container, false)
        setupRecycleView()

        setHasOptionsMenu(true)
        binding.tvData.text = "Date: $currentDate"
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

            val resultsCall = resultsAPI.getMoney("140", getSeason(), currentDate)

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
                    MatchAdapter.matchList = emptyList()
                    //megpróbálom a recycle viewba tenni az adatot az api hívás után
                    if (sportResult?.response != null) {
                        for (element in sportResult.response) {
                            //dátum kiszedése
                            val matchDate = "${
                                (element.fixture!!.date?.take(13)?.takeLast(2)?.toInt()
                                    ?.plus(2)).toString()
                            }${(element.fixture!!.date?.take(16)?.takeLast(3))}"
                            //eddig dátum
                            matchAdapter.addMatch(
                                element.fixture?.id?.let { it1 ->
                                    element.teams?.home?.name?.let { it2 ->
                                        element.teams.away?.name?.let { it3 ->
                                            Match(
                                                it1,
                                                it2,
                                                it3,
                                                element.goals!!.home, element.goals!!.away,
                                                element.teams.home.logo, element.teams.away.logo,
                                                matchDate,
                                                null
                                            )
                                        }
                                    }
                                },
                            )
                        }
                    }

                    //val homeTeam = sportResult?.response?.get(0)?.teams?.home?.name
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
