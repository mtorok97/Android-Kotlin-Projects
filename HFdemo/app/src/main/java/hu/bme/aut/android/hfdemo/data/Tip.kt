package hu.bme.aut.android.hfdemo.data

data class Tip(
    var uid: String? = null,
    var author: String? = null,
    var matchID: String? = null,
    var homeTeam: String? = null,
    var awayTeam: String? = null,
    var tip: String? = null, //HOME/DRAW/AWAY
    var points: Int = 0
)