package hu.bme.aut.android.hfdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class RoomPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val nationality: String?,
    val photo: String?
)