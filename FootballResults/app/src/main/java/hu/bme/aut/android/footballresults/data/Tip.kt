package hu.bme.aut.android.footballresults.data

data class Tip(
    var uid: String? = null,
    var author: String? = null,
    var matchID: String? = null,
    var homeTeam: String? = null,
    var awayTeam: String? = null,
    var tip: String? = null //Home/Away/X
)