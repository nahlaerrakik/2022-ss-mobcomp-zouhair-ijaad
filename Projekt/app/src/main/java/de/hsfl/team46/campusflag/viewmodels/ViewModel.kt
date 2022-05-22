package de.hsfl.team46.campusflag.viewmodels

import android.app.Application
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.hsfl.team46.campusflag.model.Host
import de.hsfl.team46.campusflag.model.Player
import de.hsfl.team46.campusflag.model.Position
import de.hsfl.team46.campusflag.repository.ApiRepository
import de.hsfl.team46.campusflag.repository.LocationRepository

class ViewModel (application: Application) : AndroidViewModel(application) {
    private val apiRepository = ApiRepository.getInstance(getApplication())
    private val locationRepository = LocationRepository()

    private var host: MutableLiveData<Host> = MutableLiveData()
    fun getHost(): MutableLiveData<Host> = host
    fun setHost(s: Editable){
        host.value = Host(null, s.toString(), null)
    }
    fun setHost(s: Host){
        host.value = s
    }


    private val gameId: MutableLiveData<Int> = MutableLiveData()
    fun getGameId(): MutableLiveData<Int> = gameId
    fun setGameId(s: Editable){
        if (s.toString().isNotEmpty()){
            gameId.value = s.toString().toInt()
        }
    }
    fun setGameId(s: Int){
        gameId.value = s
    }

    private var player: MutableLiveData<Player> = MutableLiveData()
    fun getPlayer() : MutableLiveData<Player> = player
    fun setPlayer(s: Editable){
        player.value = Player(null,
            s.toString(),
            null,
            null,
            null)
    }
    fun setPlayer(s: Player){
        player.value = s
    }

    var currentPosition: LiveData<Position> = locationRepository.getCurrentPosition()
    var currentLocation: LiveData<Position> = locationRepository.getCurrentLocation()

    var currentHostFlagPositions: Array<Position> = arrayOf()
    fun setCurrentHostFlagPositions(location: Position) {
        Log.d("-------------------------- -> setCurrentHostFlagPositions:", location.toString())
        currentHostFlagPositions = arrayOf(location)
    }

    fun getLocationRepository() : LocationRepository = locationRepository


    fun createGame(callback: (Int) -> (Unit)) {
        apiRepository.postGame(host.value!!, currentHostFlagPositions) {
            host.value = it
            gameId.value = it.game
            callback(it.game!!)
        }
    }

    fun joinGame(callback: (Boolean) -> (Unit)) {
        Log.d("-------------------------- -> JOINED PLAYER :", player.value.toString())
        player.value!!.game = gameId.value

        apiRepository.joinGame(player.value!!){
            if(it.token == null){
                Toast.makeText(getApplication(), "Name already exists",
                    Toast.LENGTH_LONG).show()
                callback(true)
            }
            else{
                player.value = it
                callback(false)
            }
        }
    }

    fun fetchPlayers(){
        if (host.value != null){
            apiRepository.getGamePlayers(
                gameId.value!!,
                host.value!!.name!!,
                host.value!!.token!!) {
                Log.d("-------------------------- -> ALL PLAYERS ", it.toString())
            }
        }
        else{
            apiRepository.getGamePlayers(
                gameId.value!!,
                player.value!!.name!!,
                player.value!!.token!!) {
                Log.d("-------------------------- -> ALL PLAYERS ", it.toString())
            }
        }
    }
}