package hu.bme.aut.android.hfdemo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object OkHttpHelper {
    fun getRates(): String {
        val client = OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .build()

        /*val request = Request.Builder()
            .url("https://api.tvmaze.com/search/shows?q=stargate")
            .get()
            .build()*/

        val request = Request.Builder()
            .url("https://api-football-beta.p.rapidapi.com/fixtures?league=140&season=2020&date=2021-04-03")
            .get()
            .addHeader("x-rapidapi-key", "c7546e1840mshbbd50343a36cd3bp1cd6a8jsnebbc644c0c12")
            .addHeader("x-rapidapi-host", "api-football-beta.p.rapidapi.com")
            .addHeader("Accept-Encoding", "identity")
            .build()

        val call = client.newCall(request)
        val response = call.execute()

        val responseStr = response.body!!.string()
        return responseStr
    }
}