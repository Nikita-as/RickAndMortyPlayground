package com.example.rickandmortyplayground.domain.use_cases

import com.example.rickandmortyplayground.domain.repository.CharacterRepository
import com.example.rickandmortyplayground.domain.models.ResultById
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int): Single<ResultById> {
        return repository.getCharacterById(id = id)
            .map { it.toResultById() }
            .subscribeOn(Schedulers.io())
    }
}