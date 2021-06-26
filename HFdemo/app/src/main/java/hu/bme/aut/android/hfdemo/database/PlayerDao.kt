package hu.bme.aut.android.hfdemo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Insert
    fun insertPlayer(player: RoomPlayer)

    @Query("SELECT * FROM player")
    fun getAllPlayers(): LiveData<List<RoomPlayer>>

    @Query("SELECT * FROM player")
    fun getAllPlayersToDeleteAll(): List<RoomPlayer>

    @Query("SELECT * FROM player WHERE id == :id")
    fun getPlayerById(id: Int?): RoomPlayer?

    @Update
    fun updatePlayer(player: RoomPlayer): Int

    @Delete
    fun deletePlayer(player: RoomPlayer)

    @Delete
    fun deleteListPlayer(player: List<RoomPlayer>)

}