package hu.bme.aut.android.hfdemo.model

data class Player(
    val id: Int,
    val name: String,
    val age: Int,
    val nationality: String?,
    val photo: String?
)