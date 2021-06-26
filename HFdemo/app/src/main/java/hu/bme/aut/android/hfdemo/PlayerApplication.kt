package hu.bme.aut.android.hfdemo

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.hfdemo.database.PlayerDatabase

class PlayerApplication : Application() {

    companion object {
        lateinit var playerDatabase: PlayerDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        playerDatabase = Room.databaseBuilder(
            applicationContext,
            PlayerDatabase::class.java,
            "player_database"
        ).fallbackToDestructiveMigration().build()
    }

}