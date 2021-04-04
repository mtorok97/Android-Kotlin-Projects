package hu.bme.aut.android.hfdemo.jsonkotlin

data class TestData3(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)

data class Paging(
    val current: Int,
    val total: Int
)

data class Parameters(
    val date: String,
    val league: String,
    val season: String
)

data class Response(
    val fixture: Fixture
)

data class Fixture2(
    val id: Int
)