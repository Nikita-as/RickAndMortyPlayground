package com.example.rickandmortyplayground.domain.repository

import com.example.rickandmortyplayground.data.remote.dto.CharactersDto
import com.example.rickandmortyplayground.data.remote.dto.ResultDto
import io.reactivex.Single

interface CharacterRepository {

    fun getAllCharacters(pageNumber: Int): Single<CharactersDto>

    fun getCharacterById(id: Int): Single<ResultDto>

}