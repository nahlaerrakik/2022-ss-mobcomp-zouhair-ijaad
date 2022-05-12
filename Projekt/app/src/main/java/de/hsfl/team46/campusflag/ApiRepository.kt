package de.hsfl.team46.campusflag

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ApiRepository(application: Application) {

    companion object {
        private var instance: ApiRepository? = null

        fun getInstance(application: Application) = instance ?: ApiRepository(application)

    }

    val requestQueue = Volley.newRequestQueue(application)

    fun postGame(game: Game, player: Player, callback: (Game) -> (Unit)) {

        val url = "https://ctf.letorbi.de/game/register"

        val `object` = JSONObject()

        try {
            //input the API parameters
            `object`.put("name", player.name)  // classe Game -> hostname points
            `object`.put("points", "")
        } catch (e: JSONException) {
            Log.e(ContentValues.TAG, "problem occurred !!!!!!!!!!!!!!!!!! ")
            e.printStackTrace()
        }

        Log.d("ApiRepository", "posting Ressource")
        val req = JsonObjectRequest(
            Request.Method.POST, url, `object`,
            {
                callback(Game(
                    it.getInt("game"),
                    it.getString("token"),
                    null,
                    null,
                    Player(
                        it.getInt("game"),
                        it.getString("name"),
                        null,
                        it.getString("token"),
                        null,
                    ),
                    null,
                    null
                ))
                Log.d("GAME FROM REPOSI", it.toString())
            },
            {
                Log.e("ApiRepository", it.toString())
            }
        )

        requestQueue.add(req)
    }

    fun joinGame(player: Player, callback: (Player) -> (Unit)) {

        val url = "https://ctf.letorbi.de/game/join"

        val `object` = JSONObject()

        try {
            //input the API parameters
            `object`.put("game", player.game)
            `object`.put("name", player.name)
            `object`.put("team", 0)
        } catch (e: JSONException) {
            Log.e(ContentValues.TAG, "problem occurred !!!!!!!!!!!!!!!!!! ")
            e.printStackTrace()
        }

        Log.d("ApiRepository", "posting Resource")
        val req = JsonObjectRequest(
            Request.Method.POST, url, `object`,
            {
                callback(
                    Player(
                        it.getInt("game"),
                        it.getString("name"),
                        it.getInt("team"),
                        it.getString("token"),
                        null
                    )
                )
                Log.d("GAME JOIN", it.toString())
            },
            {
                Log.e("ApiRepository", it.toString())
            }
        )

        requestQueue.add(req)
    }

    fun fetchPlayersFromAPI(game: Game, callback: (Game) -> (Unit)) {

        val url = "https://ctf.letorbi.de/players"

        val objectauth = JSONObject(
            mapOf(
                "game" to game.game,
                "auth" to mapOf("name" to game.host!!.name, "token" to game.host.token)
            )
        )

        Log.d("FETCH objectauth----------------", objectauth.toString())

        val req = JsonObjectRequest(
            Request.Method.POST, url, (objectauth),
            {
                callback(
                    Game(
                        it.getInt("game"),
                        null,
                        null,
                        null,
                        game.host,
                        it.getInt("state"),
                        it.getJSONArray("players")
                    )
                )
                Log.d("FETCH PLAYERS---------------->         ", it.toString())
            },
            {
                Log.e("ApiRepository", it.toString())
            }
        )

        requestQueue.add(req)

    }

//    fun fetchPlayers(player: Player, callback: (Player) -> (Unit)) {
//
//        val url = "https://ctf.letorbi.de/game/players"
//
//        val objectauth = JSONObject(
//            mapOf(
//                "game" to player.game,
//                "auth" to mapOf(
//                    "name" to player.name,
//                    "token" to player.token
//                )
//            )
//        )
//
//        val `object` = JSONObject()
//
//        try {
//            //input the API parameters
////            `objectauth`.put("name", player.name)
////            `objectauth`.put("token",player.token)
//
//            `object`.put("game", player.game)
//            `object`.put("auth",objectauth)
//
//            Log.d("request oobjectauth gggggggggggggggggggggg", objectauth.toString())
//        } catch (e: JSONException) {
//            Log.e(ContentValues.TAG, "problem occurred !!!!!!!!!!!!!!!!!! ")
//            e.printStackTrace()
//        }
//
//        Log.d("ApiRepository", "posting Resource")
//        val req = JsonObjectRequest(
//            Request.Method.POST, url, (objectauth),
//            {
//                callback(
//                    Player(
//                        it.getInt("game"),
//                        it.getString("state"),
//                        it.getInt("team"),
//                        it.getString("token"),
//                        it.getString("addr")
//                    )
//                )
//                Log.d("FETCH PLAYERS---------------->         ", it.toString())
//            },
//            {
//                Log.e("ApiRepository", it.toString())
//            }
//        )
//
//        requestQueue.add(req)
//    }
}