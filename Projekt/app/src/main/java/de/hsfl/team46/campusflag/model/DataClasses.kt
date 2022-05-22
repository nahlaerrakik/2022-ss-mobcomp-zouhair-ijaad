package de.hsfl.team46.campusflag.model

import org.json.JSONArray


data class Host (
    var game: Int?
    , var name: String?
    , var token: String?
)

data class Game(
    val game: Int?
    , val state: Int?
    , val players: JSONArray?
)

data class Player (
    var game: Int?
    , var name: String?
    , var team: Int?
    , var token: String?
    , var addr : String?
    )

data class Position(
    val lat: Double = 0.0,
    val long: Double = 0.0
)

