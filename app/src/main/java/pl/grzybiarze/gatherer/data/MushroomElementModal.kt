package pl.grzybiarze.gatherer.data

import pl.grzybiarze.gatherer.enum.MushroomStatus

data class MushroomElementModal(
    val image: String,//TODO: zamienić
    var name: String,
    val status: MushroomStatus
)
