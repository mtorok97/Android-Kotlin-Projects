package hu.bme.aut.android.hfdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.hfdemo.PlayerApplication
import hu.bme.aut.android.hfdemo.model.Player
import hu.bme.aut.android.hfdemo.repository.Repository
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val repository: Repository
    val allPlayers: LiveData<List<Player>>

    init {
        val playerDao = PlayerApplication.playerDatabase.playerDao()
        repository = Repository(playerDao)
        allPlayers = repository.getAllPlayers()
    }

    fun insert(player: Player) = viewModelScope.launch {
        repository.insert(player)
    }

    fun delete(player: Player) = viewModelScope.launch {
        repository.delete(player)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun modify(player: Player) = viewModelScope.launch {
        repository.modify(player)
    }
}