package hu.bme.aut.android.hfdemo.data

data class Match(
        val matchId: Int,
        val homeTeam: String,
        val awayTeam: String,
        val homeGoals: Int?,
        val awayGoals: Int?,
        val homeImageURL: String?,
        val awayImageURL: String?,
        val matchDate: String,
)