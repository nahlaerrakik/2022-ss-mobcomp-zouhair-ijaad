package de.hsfl.team46.campusflag

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray
import org.json.JSONObject

class ViewModel (application: Application) : AndroidViewModel(application) {

    private val apiRepository = ApiRepository.getInstance(getApplication())

    private val gameId: MutableLiveData<Int> = MutableLiveData()
    private var hostname: MutableLiveData<String> = MutableLiveData("no hostname yet")
    private var hostPlayer: MutableLiveData<Player> = MutableLiveData()
    private var player: MutableLiveData<String> = MutableLiveData("no player yet")

    fun getGameId(): MutableLiveData<Int> = gameId
    fun getHostname() : MutableLiveData<String> = hostname
    fun getHostPlayer() : MutableLiveData<Player> = hostPlayer
    fun getPlayer() : MutableLiveData<String> = player

    fun setHostname(s: Editable){
        hostname.value = s.toString()
    }

    fun setHostPlayer(s: Editable){
        hostPlayer.value = Player(null, s.toString(), null, null, null)
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
        Log.d("--------------------------haaa hostname", hostname.value.toString())
        Log.d("--------------------------haaa hostplayer", hostPlayer.value?.name.toString())
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
                    Log.d("***************gameId", gameId.value.toString())
                    Log.d("***************hostPlayer", hostPlayer.value.toString())
                }
            }
        }

        // listofPlayers.value!!.add(0,host)
    }

    fun joinGame() {
        Log.d("--------------------------haaa game id from join", gameId.value.toString())
        Log.d("--------------------------haaa player from join", player.value.toString())


        val playerPost = Player(
            gameId.value,
            player.value,
            null,
            null,
            null
        )

        apiRepository.joinGame(playerPost) {
            gameId.value = it.game
//          hostname.value = it.host.name
        }

    }

    fun fetchPlayers(){
        //hostPlayer.value?.toString()?.let { Log.e("--------------------------haaa hostplayer", it) }

//        val players: MutableList<Player> = arrayListOf()

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
                    Log.d("--------------------------haaa fetchPlayersFromAPI", it.toString())
                }
            }
        }
    }

//    fun getGamePlayers() {
////        Log.d("--------------------------haaa game id from join", gameId.value.toString())
////        Log.d("--------------------------haaa player from join", player.value.toString())
////        val playerPost = Player(
////            gameId.value,
////            player.value.toString(),
////            null,
////            null,
////            null
////        )
//
//
//        Log.d("getGamePlayers- FUNKTION ","---- VIEW MODEL ----")
//        Log.d("player zttztztztztz", player.toString())
//        Log.d("playerPost zttztztztztz", playerPost.toString())
//        Log.d("playerObject zttztztztztz", playerObject.toString())
//        apiRepository.fetchPlayers(playerPost) {
////            gameId.value = it.game
////            hostname.value = it.host.name
//
//            Log.d("JOIN RESPONSE FROM VIEWMODEL", it.toString())
//        }
//    }
//
}