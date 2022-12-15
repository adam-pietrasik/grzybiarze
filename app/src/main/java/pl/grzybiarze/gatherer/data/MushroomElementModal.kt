package pl.grzybiarze.gatherer.data

import pl.grzybiarze.gatherer.enum.MushroomStatus

data class MushroomElementModal(
    val image: String,
    var name: String,
    val description: String,
    val status: MushroomStatus

)
