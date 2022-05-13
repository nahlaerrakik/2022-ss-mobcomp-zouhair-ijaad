package de.hsfl.team46.campusflag.viewmodels

import android.app.Application
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import de.hsfl.team46.campusflag.model.Game
import de.hsfl.team46.campusflag.model.Player
import de.hsfl.team46.campusflag.repository.ApiRepository

class ViewModel (application: Application) : AndroidViewModel(application) {

    private val apiRepository = ApiRepository.getInstance(getApplication())

    private val gameId: MutableLiveData<Int> = MutableLiveData()
    private var hostname: MutableLiveData<String> = MutableLiveData("no hostname yet")
    private var hostPlayer: MutableLiveData<Player> = MutableLiveData()
    private var player: MutableLiveData<String> = MutableLiveData("no player yet")

    fun getGameId(): MutableLiveData<Int> = gameId
    fun getHostPlayer() : MutableLiveData<Player> = hostPlayer
    fun getPlayer() : MutableLiveData<String> = player



    fun setHostPlayer(s: Editable){
        hostPlayer.value = Player(null,
            s.toString(),
            null,
            null,
            null)
    }
    fun setPlayer(s: Editable){
        player.value = s.toString()
    }
    fun setGameId(s: Editable){
        if (s.toString().isNotEmpty()){
            gameId.value = s.toString().toInt()
        }
    }

    fun createGame() {
       Log.d("-------------------------- HOSTPLAYER", hostPlayer.value?.name.toString())
        val postHostName = hostname.value.toString()
        val host = Player(
            null,
            postHostName,
            null,
            null,
            null
        )
        val game = hostPlayer.value?.let {
            Game(
                gameId.value,
                null,
                null,
                null,
                it,
                null,
                null
            )
        }

        if (game != null) {
            hostPlayer.value?.let {
                apiRepository.postGame(game, it) {
                    gameId.value = it.game
                    hostname.value = it.host?.name
                    hostPlayer.value = it.host

                    Log.d("GAME RESPONSE FROM VIEWMODEL", it.toString())
                    Log.d("-------------------------- -> CREATED HOST ", hostPlayer.value.toString())
                }
            }
        }
    }

    fun joinGame(callback: (Boolean) -> (Unit)) {
        Log.d("-------------------------- -> JOINED PLAYER :", player.value.toString())

        val playerPost = Player(
            gameId.value,
            player.value,
            null,
            null,
            null
        )

        apiRepository.joinGame(playerPost){
            if(it.token == null){
                Toast.makeText(getApplication(), "Name already exists",
                    Toast.LENGTH_LONG).show()
                callback(true)
            }
            else{
                callback(false)
            }
        }

    }

    fun fetchPlayers(){
        val game = hostPlayer.value?.let {
            Game(
                gameId.value,
                null,
                null,
                null,
                hostPlayer.value!!,
                null,
                null
            )
        }

        hostPlayer.value?.let {
            game?.let { it1 ->
                apiRepository.fetchPlayersFromAPI(it1) {
                    Log.d("-------------------------- -> ALL PLAYERS ", it.toString())
                }
            }
        }
    }

}