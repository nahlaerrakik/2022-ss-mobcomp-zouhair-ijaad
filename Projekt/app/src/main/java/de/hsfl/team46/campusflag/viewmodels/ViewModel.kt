package de.hsfl.team46.campusflag.viewmodels

import android.app.Application
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import de.hsfl.team46.campusflag.model.*
import de.hsfl.team46.campusflag.repository.ApiRepository
import de.hsfl.team46.campusflag.repository.LocationRepository

class ViewModel (application: Application) : AndroidViewModel(application) {
    private val TOP_LEFT_COORDS = Position(54.778514, 9.442749)
    private val BOTTOM_RIGHT_COORDS = Position(54.769009, 9.464722)

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

    private val game: MutableLiveData<Game> = MutableLiveData()
    fun getGame(): MutableLiveData<Game> = game
    fun setGame(s: Game){
        game.value = s
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
        currentHostFlagPositions = arrayOf(location)
    }

    var points: MutableLiveData<List<Point>> = MutableLiveData()

    private val pcolor: MutableLiveData<String> = MutableLiveData("#FF0303")
    fun getPcolor() : MutableLiveData<String> = pcolor
    fun setPcolor(s: String){
        pcolor.value = s
    }

    fun getLocationRepository() : LocationRepository = locationRepository


    fun createGame(callback: (Int) -> (Unit)) {
        apiRepository.postGame(host.value!!, currentHostFlagPositions) {
            host.value = it
            gameId.value = it.game
            game.value = Game(it.game, null, null, null)
            callback(it.game!!)
        }
    }

    fun joinGame(callback: (Boolean) -> (Unit)) {
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
                game.value!!.players = it.players
                Log.d("Fetch Players", it.toString())
            }
        }
        else{
            apiRepository.getGamePlayers(
                gameId.value!!,
                player.value!!.name!!,
                player.value!!.token!!) {
                game.value!!.players = it.players
                Log.d("Fetch Players", it.toString())
            }
        }
    }

    fun fetchPoints(callback: (Game) -> (Unit)){
        if (host.value != null){
            apiRepository.getGamePoints(
                gameId.value!!,
                host.value!!.name!!,
                host.value!!.token!!) {
                game.value!!.points = it.points
                points.value = it.points!!
                callback(
                    it
                )
            }
        }
        else {
            apiRepository.getGamePoints(
                gameId.value!!,
                player.value!!.name!!,
                player.value!!.token!!) {
                game.value!!.points = it.points
                points.value = it.points!!
                callback(
                    it
                )
            }
        }
    }

    fun removePlayer(callback: (Boolean) -> (Unit)) {
        if (player.value != null){
            apiRepository.removePlayer(player.value!!){
                if(it.status == -1){
                    Toast.makeText(getApplication(), it.message,
                        Toast.LENGTH_LONG).show()
                    player.value = null
                    callback(true)
                }
                else{
                    callback(false)
                }
            }
        }
        else{
            callback(false)
        }
    }

    fun getLatScaling(lat:Double): Double {
        return (lat - TOP_LEFT_COORDS.lat) / (BOTTOM_RIGHT_COORDS.lat - TOP_LEFT_COORDS.lat)
    }

    fun getLongScaling(long:Double): Double {
        return (long - TOP_LEFT_COORDS.long) / (BOTTOM_RIGHT_COORDS.long - TOP_LEFT_COORDS.long)
    }
}