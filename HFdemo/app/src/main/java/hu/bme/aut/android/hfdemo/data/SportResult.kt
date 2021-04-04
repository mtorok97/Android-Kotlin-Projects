package hu.bme.aut.android.hfdemo.data

import com.squareup.moshi.JsonClass
import hu.bme.aut.android.hfdemo.jsonkotlin.Response

//@JsonClass(generateAdapter = true)
//class TestData2 : ArrayList<TestData2Item>()

@JsonClass(generateAdapter = true)
class AllData(
    val errors: List<Any>?,
    val getResponse: String?,
    val paging: Paging?,
    val parameters: Parameters?,
    val response: List<Response_http>?,
    val results: Int?
)

@JsonClass(generateAdapter = true)
class Paging(
    val current: Int?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class Parameters(
    val date: String?,
    val league: String?,
    val season: String?
)

@JsonClass(generateAdapter = true)
class Response_http(
    val fixture: Fixture?,
    val goals: Goals?,
    val league: League?,
    val score: Score?,
    val teams: Teams?
)

@JsonClass(generateAdapter = true)
class Fixture(
    val date: String?,
    val id: Int?,
    val periods: Periods?,
    val referee: String?,
    val status: Status?,
    val timestamp: Int?,
    val timezone: String?,
    val venue: Venue?
)

@JsonClass(generateAdapter = true)
class Goals(
    val away: Int?,
    val home: Int?
)

@JsonClass(generateAdapter = true)
class League(
    val country: String?,
    val flag: String?,
    val id: Int?,
    val logo: String?,
    val name: String?,
    val round: String?,
    val season: Int?
)

@JsonClass(generateAdapter = true)
class Score(
    val extratime: Extratime?,
    val fulltime: Fulltime?,
    val halftime: Halftime?,
    val penalty: Penalty?
)

@JsonClass(generateAdapter = true)
class Teams(
    val away: Away?,
    val home: Home?
)

@JsonClass(generateAdapter = true)
class Periods(
    val first: Int?,
    val second: Int?
)

@JsonClass(generateAdapter = true)
class Status(
    val elapsed: Int?,
    val long: String?,
    val short: String?
)

@JsonClass(generateAdapter = true)
class Venue(
    val city: String?,
    val id: Int?,
    val name: String?
)

@JsonClass(generateAdapter = true)
class Extratime(
    val away: Any?,
    val home: Any?
)

@JsonClass(generateAdapter = true)
class Fulltime(
    val away: Int?,
    val home: Int?
)

@JsonClass(generateAdapter = true)
class Halftime(
    val away: Int?,
    val home: Int?
)

@JsonClass(generateAdapter = true)
class Penalty(
    val away: Any?,
    val home: Any?
)

@JsonClass(generateAdapter = true)
class Away(
    val id: Int?,
    val logo: String?,
    val name: String?,
    val winner: Boolean?
)

@JsonClass(generateAdapter = true)
class Home(
    val id: Int?,
    val logo: String?,
    val name: String?,
    val winner: Boolean?
)