package com.example.rickandmortyplayground.domain.use_cases

import com.example.rickandmortyplayground.domain.models.Characters
import com.example.rickandmortyplayground.domain.repository.CharacterRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(pageNumber: Int): Single<Characters> {
        return repository.getAllCharacters(pageNumber = pageNumber)
            .map { it.toCharacter() }
            .subscribeOn(Schedulers.io())
    }
}