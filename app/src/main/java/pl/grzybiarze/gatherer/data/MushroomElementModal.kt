package pl.grzybiarze.gatherer.data

import pl.grzybiarze.gatherer.enum.MushroomStatus
import java.io.Serializable

data class MushroomElementModal(
    val image: String,
    var name: String,
    val description: String,
    val status: MushroomStatus

):Serializable
