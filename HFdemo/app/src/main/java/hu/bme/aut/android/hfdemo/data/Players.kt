package hu.bme.aut.android.hfdemo.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PlayersData(
    val errors: List<Any>?,
    val getResp: String?,
    val paging: PagingP?,
    val parameters: ParametersP?,
    val response: List<ResponseP>?,
    val results: Int?
)

@JsonClass(generateAdapter = true)
class PagingP(
    val current: Int?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class ParametersP(
    val season: String?,
    val team: String?
)

@JsonClass(generateAdapter = true)
class ResponseP(
    val player: Player?,
    val statistics: List<Statistic>?
)

@JsonClass(generateAdapter = true)
class Player(
    val age: Int?,
    val birth: Birth?,
    val firstname: String?,
    val height: String?,
    val id: Int?,
    val injured: Boolean?,
    val lastname: String?,
    val name: String?,
    val nationality: String?,
    val photo: String?,
    val weight: String?
)

@JsonClass(generateAdapter = true)
class Statistic(
    val cards: Cards?,
    val dribbles: Dribbles?,
    val duels: Duels?,
    val fouls: Fouls?,
    val games: Games?,
    val goals: GoalsP?,
    val league: LeagueP?,
    val passes: Passes?,
    val penalty: PenaltyP?,
    val shots: Shots?,
    val substitutes: Substitutes?,
    val tackles: Tackles?,
    val team: Team?
)

@JsonClass(generateAdapter = true)
class Birth(
    val country: String?,
    val date: String?,
    val place: String?
)

@JsonClass(generateAdapter = true)
class Cards(
    val red: Int?,
    val yellow: Int?,
    val yellowred: Int?
)

@JsonClass(generateAdapter = true)
class Dribbles(
    val attempts: Int?,
    val past: Any?,
    val success: Int?
)

@JsonClass(generateAdapter = true)
class Duels(
    val total: Int?,
    val won: Int?
)

@JsonClass(generateAdapter = true)
class Fouls(
    val committed: Int?,
    val drawn: Int?
)

@JsonClass(generateAdapter = true)
class Games(
    val appearences: Int?,
    val captain: Boolean?,
    val lineups: Int?,
    val minutes: Int?,
    val number: Any?,
    val position: String?,
    val rating: String?
)

@JsonClass(generateAdapter = true)
class GoalsP(
    val assists: Int?,
    val conceded: Int?,
    val saves: Any?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class LeagueP(
    val country: String?,
    val flag: String?,
    val id: Int?,
    val logo: String?,
    val name: String?,
    val season: Int?
)

@JsonClass(generateAdapter = true)
class Passes(
    val accuracy: Int?,
    val key: Int?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class PenaltyP(
    val commited: Any?,
    val missed: Int?,
    val saved: Any?,
    val scored: Int?,
    val won: Any?
)

@JsonClass(generateAdapter = true)
class Shots(
    val on: Int?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class Substitutes(
    val bench: Int?,
    val subin: Int?,
    val subout: Int?
)

@JsonClass(generateAdapter = true)
class Tackles(
    val blocks: Int?,
    val interceptions: Int?,
    val total: Int?
)

@JsonClass(generateAdapter = true)
class Team(
    val id: Int?,
    val logo: String?,
    val name: String?
)