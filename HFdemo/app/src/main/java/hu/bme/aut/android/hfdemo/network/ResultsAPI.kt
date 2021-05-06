package hu.bme.aut.android.hfdemo.network

import hu.bme.aut.android.hfdemo.data.AllData
import retrofit2.Call
import retrofit2.http.*
import java.util.*

// host: "https://api-football-beta.p.rapidapi.com"
//
// PATH: /fixtures
//
// QUERY arguments: ?   league=140 //ez La Liga

interface ResultsAPI {
    @Headers("x-rapidapi-key: c7546e1840mshbbd50343a36cd3bp1cd6a8jsnebbc644c0c12",
                    "x-rapidapi-host: api-football-beta.p.rapidapi.com",
                    "Accept-Encoding: identity"
    )
    @GET("/fixtures")
    fun getMatch(@Query("league") league: String,
                 @Query("season") season: String,
                 @Query("date") date: String
    ) : Call<AllData>
}