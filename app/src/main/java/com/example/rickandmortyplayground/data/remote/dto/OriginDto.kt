package com.example.rickandmortyplayground.data.remote.dto

import com.example.rickandmortyplayground.domain.models.Origin

data class OriginDto(
    val name: String?,
    val url: String?
) {
    fun toOrigin(): Origin {
        return Origin(
            name = name,
            url = url
        )
    }
}