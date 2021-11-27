package com.example.rickandmortyplayground.data.remote.dto

import com.example.rickandmortyplayground.domain.models.Characters

data class CharactersDto(
    val info: InfoDto?,
    val results: List<ResultDto>?
) {
    fun toCharacter(): Characters {
        return Characters(
            info = info?.toInfo(),
            results = results?.map { it.toResult() }
        )
    }
}