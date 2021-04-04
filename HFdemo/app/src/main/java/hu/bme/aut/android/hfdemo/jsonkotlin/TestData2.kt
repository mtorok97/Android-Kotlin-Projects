package hu.bme.aut.android.hfdemo.jsonkotlin

class TestData2 : ArrayList<TestData2Item>()

data class TestData2Item(
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
)

data class Fixture(
    val date: String,
    val id: Int,
    val periods: Periods,
    val referee: String,
    val status: Status,
    val timestamp: Int,
    val timezone: String,
    val venue: Venue
)

data class Goals(
    val away: Int,
    val home: Int
)

data class League(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val round: String,
    val season: Int
)

data class Score(
    val extratime: Extratime,
    val fulltime: Fulltime,
    val halftime: Halftime,
    val penalty: Penalty
)

data class Teams(
    val away: Away,
    val home: Home
)

data class Periods(
    val first: Int,
    val second: Int
)

data class Status(
    val elapsed: Int,
    val long: String,
    val short: String
)

data class Venue(
    val city: String,
    val id: Int,
    val name: String
)

data class Extratime(
    val away: Any,
    val home: Any
)

data class Fulltime(
    val away: Int,
    val home: Int
)

data class Halftime(
    val away: Int,
    val home: Int
)

data class Penalty(
    val away: Any,
    val home: Any
)

data class Away(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Boolean
)

data class Home(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Boolean
)