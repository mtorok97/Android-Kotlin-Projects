package hu.bme.aut.android.hfdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.hfdemo.database.PlayerDao
import hu.bme.aut.android.hfdemo.database.RoomPlayer
import hu.bme.aut.android.hfdemo.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val playerDao: PlayerDao) {

    fun getAllPlayers(): LiveData<List<Player>> {
        return playerDao.getAllPlayers()
            .map { roomPlayers ->
                roomPlayers.map { roomPlayer ->
                    roomPlayer.toDomainModel()
                }
            }
    }

    suspend fun insert(player: Player) = withContext(Dispatchers.IO) {
        playerDao.insertPlayer(player.toRoomModel())
    }

    suspend fun delete(player: Player) = withContext(Dispatchers.IO) {
        val roomPlayer = playerDao.getPlayerById(player.id) ?: return@withContext
        playerDao.deletePlayer(roomPlayer)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        val roomPlayerList = playerDao.getAllPlayersToDeleteAll()
        playerDao.deleteListPlayer(roomPlayerList)
    }

    suspend fun modify(player: Player) = withContext(Dispatchers.IO) {
        val roomPlayer = player.toRoomModel2()
        playerDao.updatePlayer(roomPlayer)
    }

    private fun RoomPlayer.toDomainModel(): Player {
        return Player(
            id = id,
            name = name,
            age = age,
            nationality = nationality,
            photo = photo
        )
    }

    private fun Player.toRoomModel(): RoomPlayer {
        return RoomPlayer(
            name = name,
            age = age,
            nationality = nationality,
            photo = photo
        )
    }

    private fun Player.toRoomModel2(): RoomPlayer {
        return RoomPlayer(
            id = id,
            name = name,
            age = age,
            nationality = nationality,
            photo = photo
        )
    }
}