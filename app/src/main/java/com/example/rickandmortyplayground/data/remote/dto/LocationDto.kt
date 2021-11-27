package com.example.rickandmortyplayground.data.remote.dto

import com.example.rickandmortyplayground.domain.models.Location

data class LocationDto(
    val name: String?,
    val url: String?
) {
    fun toLocation(): Location {
        return Location(
            name = name,
            url = url
        )
    }
}