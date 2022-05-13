package de.hsfl.team46.campusflag.model

import org.json.JSONArray

data class Game(
    val game: Int?
    , val token: String?
    , val teamOne: Team?
    , val teamTwo: Team?
    , val host: Player?
    , val state: Int?
    , val players: JSONArray?
)

data class Player (
    val game: Int?
    , val name: String?
    , val team: Int?
    , val token: String?
    , val addr : String?
    )

data class Team (
     val id : Int
    , val color : String
)


// data class Game : gameId, Host, MutableList<Player> , state

// date class Player :  name, tokenPlayer, addr, team, IsHost

