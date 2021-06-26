package hu.bme.aut.android.hfdemo.network

import hu.bme.aut.android.hfdemo.data.PlayersData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PlayersAPI {
    @Headers(
        "x-rapidapi-key: c7546e1840mshbbd50343a36cd3bp1cd6a8jsnebbc644c0c12",
        "x-rapidapi-host: api-football-beta.p.rapidapi.com",
        "Accept-Encoding: identity"
    )
    @GET("/players")
    fun getPlayers(
        @Query("season") season: String,
        @Query("team") team: String
    ): Call<PlayersData>
}