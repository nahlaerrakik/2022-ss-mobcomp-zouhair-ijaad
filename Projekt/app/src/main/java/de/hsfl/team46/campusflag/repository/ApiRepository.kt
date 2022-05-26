package de.hsfl.team46.campusflag.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import de.hsfl.team46.campusflag.model.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ApiRepository(application: Application) {

    companion object {
        private var instance: ApiRepository? = null

        fun getInstance(application: Application) = instance ?: ApiRepository(application)
    }

    private val requestQueue = Volley.newRequestQueue(application)

    fun postGame(host: Host, points:  Array<Position>, callback: (Host) -> (Unit)) {
        val url = "https://ctf.letorbi.de/game/register"

        val `object` = JSONObject()

        try {
            //input the API parameters
            `object`.put("name", host.name)
            `object`.put("points", JSONArray(Gson().toJson(points)))
        } catch (e: JSONException) {
            Log.e(ContentValues.TAG, "problem occurred !!!!!!!!!!!!!!!!!! ")
            e.printStackTrace()
        }

        Log.d("ApiRepository", "posting Resource")
        val req = JsonObjectRequest(
            Request.Method.POST, url, `object`,
            {
                callback(
                    Host(
                        it.getInt("game"),
                        it.getString("name"),
                        it.getString("token")
                    )
                )
            },
            {
                Log.e("FAILED -> GAME FROM ApiRepository", it.toString())
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
                Log.e("ApiRepository", it.networkResponse.data.decodeToString())
                callback(
                    Player(null, null, null, null,null)
                )
            }
        )

        requestQueue.add(req)
    }

    fun getGamePlayers(gameId: Int, name: String, token: String, callback: (Game) -> (Unit)) {

        val url = "https://ctf.letorbi.de/players"

        val objectauth = JSONObject(
            mapOf(
                "game" to gameId,
                "auth" to mapOf("name" to name, "token" to token)
            )
        )

        val req = JsonObjectRequest(
            Request.Method.POST, url, (objectauth),
            {
                callback(
                    Game(
                        it.getInt("game"),
                        it.getInt("state"),
                        it.getJSONArray("players"),
                        null,
                    )
                )
            },
            {
                Log.e("ApiRepository", it.toString())
            }
        )

        requestQueue.add(req)
    }

    fun getGamePoints(gameId: Int, name: String, token: String, callback: (Game) -> (Unit)) {

        val url = "https://ctf.letorbi.de/points?simulation"

        val objectauth = JSONObject(
            mapOf(
                "game" to gameId,
                "auth" to mapOf("name" to name, "token" to token)
            )
        )

        val req = JsonObjectRequest(
            Request.Method.POST, url, (objectauth),
            {
                callback(
                    Game(
                        it.getInt("game"),
                        it.getInt("state"),
                        null,
                         Gson().fromJson(it.getJSONArray("points").toString() , Array<Point>::class.java).toList()
                    )
                )
            },
            {
                Log.e("ApiRepository", it.toString())
            }
        )

        requestQueue.add(req)
    }

    fun removePlayer(player: Player, callback: (Response) -> (Unit)) {

        val url = "https://ctf.letorbi.de/player/remove"

        val objectauth = JSONObject(
            mapOf(
                "game" to player.game,
                "name" to player.name,
                "auth" to mapOf("name" to player.name, "token" to player.token)
            )
        )

        val req = JsonObjectRequest(
            Request.Method.POST, url, (objectauth),
            {
                callback(
                    Response(
                        1,
                        it.toString()
                    )
                )
            },
            {
                Log.e("ApiRepository", it.toString())
                callback(
                    Response(
                        -1,
                        it.networkResponse.data.decodeToString()
                    )
                )
            }
        )

        requestQueue.add(req)
    }

}