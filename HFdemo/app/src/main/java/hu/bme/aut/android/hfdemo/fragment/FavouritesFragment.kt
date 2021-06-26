package hu.bme.aut.android.hfdemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hfdemo.R
import hu.bme.aut.android.hfdemo.adapter.PlayerAdapter
import hu.bme.aut.android.hfdemo.data.PlayersData
import hu.bme.aut.android.hfdemo.databinding.FragmentFavouritesBinding
import hu.bme.aut.android.hfdemo.model.Player
import hu.bme.aut.android.hfdemo.network.PlayersAPI
import hu.bme.aut.android.hfdemo.viewmodel.PlayerViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import kotlin.random.Random


class FavouritesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var playerAdapter: PlayerAdapter

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)

        val spinner: Spinner = binding.spinner
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.fav_teams_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        playerAdapter = this.context?.let { PlayerAdapter(it) }!!
        binding.root.findViewById<RecyclerView>(R.id.fav_player_list).adapter = playerAdapter
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        playerViewModel.allPlayers.observe(viewLifecycleOwner, { players ->
            playerAdapter.submitList(players)
        })
        //getPlayers()
        return binding.root
    }

    fun getPlayers(teadmID: Int) {
        playerViewModel.deleteAll()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-football-beta.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val playersAPI = retrofit.create(PlayersAPI::class.java)

        val resultsCall = playersAPI.getPlayers(getSeason(), "$teadmID")

        resultsCall.enqueue(object : Callback<PlayersData> {
            override fun onFailure(call: Call<PlayersData>, t: Throwable) {
                //hiba esetén
            }

            override fun onResponse(
                call: Call<PlayersData>,
                response: Response<PlayersData>
            ) {
                val players = response.body()

                if (players?.response != null) {
                    for (element in players.response) {
                        playerViewModel.insert(
                            Player(
                                id = Random.nextInt(),
                                name = element.player?.name.toString(),
                                age = element.player?.age ?: 0,
                                nationality = element.player?.nationality,
                                photo = element.player?.photo
                            )
                        )
                    }
                }
            }
        })
    }

    private fun getSeason(): String {
        //Júliusig az előző 2020-as szezont jelzi ki, augusztól már az új szezon meccseit
        return if (Calendar.getInstance().get(Calendar.MONTH) + 1 <= 7) {
            "${Calendar.getInstance().get(Calendar.YEAR) - 1}"
        } else {
            "${Calendar.getInstance().get(Calendar.YEAR)}"
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            1 -> getPlayers(529) //Barca id:529
            2 -> getPlayers(541) //Real id:541
            3 -> getPlayers(530) //Atleti id:530
            4 -> getPlayers(536) //Sevilla id:536
            5 -> getPlayers(33) //United id:33
            6 -> getPlayers(50) //City id:
            7 -> getPlayers(49) //Chelsea id:49
            8 -> getPlayers(40) //Liverpool id:40
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}