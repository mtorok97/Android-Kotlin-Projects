package hu.bme.aut.android.hfdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomPlayer::class]
)

abstract class PlayerDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

}