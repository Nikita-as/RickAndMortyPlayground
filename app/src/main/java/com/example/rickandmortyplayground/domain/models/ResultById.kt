package com.example.rickandmortyplayground.domain.models

import com.example.rickandmortyplayground.domain.models.Location
import com.example.rickandmortyplayground.domain.models.Origin


data class ResultById(
    val id: Int?,
    val image: String?,
    val name: String?,
    val species: String?,
    val status: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val type: String?,
    val episode: List<String>?
)