package hu.bme.aut.android.hfdemo.jsonkotlin

data class PlayersData(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)

data class PagingP(
    val current: Int,
    val total: Int
)

data class ParametersP(
    val season: String,
    val team: String
)

data class ResponseP(
    val player: Player,
    val statistics: List<Statistic>
)

data class Player(
    val age: Int,
    val birth: Birth,
    val firstname: String,
    val height: String,
    val id: Int,
    val injured: Boolean,
    val lastname: String,
    val name: String,
    val nationality: String,
    val photo: String,
    val weight: String
)

data class Statistic(
    val cards: Cards,
    val dribbles: Dribbles,
    val duels: Duels,
    val fouls: Fouls,
    val games: Games,
    val goals: Goals,
    val league: League,
    val passes: Passes,
    val penalty: Penalty,
    val shots: Shots,
    val substitutes: Substitutes,
    val tackles: Tackles,
    val team: Team
)

data class Birth(
    val country: String,
    val date: String,
    val place: String
)

data class Cards(
    val red: Int,
    val yellow: Int,
    val yellowred: Int
)

data class Dribbles(
    val attempts: Int,
    val past: Any,
    val success: Int
)

data class Duels(
    val total: Int,
    val won: Int
)

data class Fouls(
    val committed: Int,
    val drawn: Int
)

data class Games(
    val appearences: Int,
    val captain: Boolean,
    val lineups: Int,
    val minutes: Int,
    val number: Any,
    val position: String,
    val rating: String
)

data class GoalsP(
    val assists: Int,
    val conceded: Int,
    val saves: Any,
    val total: Int
)

data class LeagueP(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int
)

data class Passes(
    val accuracy: Int,
    val key: Int,
    val total: Int
)

data class PenaltyP(
    val commited: Any,
    val missed: Int,
    val saved: Any,
    val scored: Int,
    val won: Any
)

data class Shots(
    val on: Int,
    val total: Int
)

data class Substitutes(
    val bench: Int,
    val `in`: Int,
    val `out`: Int
)

data class Tackles(
    val blocks: Int,
    val interceptions: Int,
    val total: Int
)

data class Team(
    val id: Int,
    val logo: String,
    val name: String
)