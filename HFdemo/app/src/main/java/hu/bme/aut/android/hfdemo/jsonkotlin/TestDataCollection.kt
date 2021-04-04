package hu.bme.aut.android.hfdemo.jsonkotlin

class TestData : ArrayList<TestDataItem>()

data class TestDataItem(
    val fixture: Fixture1,
    val goals: Goals1,
    val league: League1,
    val teams: Teams1
)

data class Fixture1(
    val date: String,
    val id: Int,
    val periods: Periods1,
    val referee: String,
    val status: Status1,
    val timestamp: Int,
    val timezone: String,
    val venue: Venue1
)

data class Goals1(
    val away: Any,
    val home: Any
)

data class League1(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val round: String,
    val season: Int
)

data class Teams1(
    val away: Away1,
    val home: Home1
)

data class Periods1(
    val first: Any,
    val second: Any
)

data class Status1(
    val elapsed: Any,
    val long: String,
    val short: String
)

data class Venue1(
    val city: String,
    val id: Int,
    val name: String
)

data class Away1(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Any
)

data class Home1(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Any
)