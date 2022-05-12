package de.hsfl.team46.campusflag

import org.json.JSONArray
import org.json.JSONObject


data class Game(
    val game: Int?
    , val token: String?
    , val teamOne: Team?
    , val teamTwo: Team?
    , val host: Player?
    , val state: Int?
    , val players: JSONArray?
    )

data class Team (
    val players: MutableList<Player>
    , val id : Int
    , val color : String
    )

data class Player (
    val game: Int?
    , val name: String?
    , val team: Int?
    , val token: String?
    , val addr : String?
    )

//data class Player (val game: Int?, val name : String, val isHost : Boolean?)

// data class Game : gameId, Host, MutableList<Player> , state

// date class Host : gameId, name, tokenHost, addr, team

// date class Player : gameId, name, tokenPlayer, addr, team