package com.example.rickandmortyplayground.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortyplayground.domain.models.Characters
import com.example.rickandmortyplayground.domain.use_cases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    var compositeDisposable = CompositeDisposable()
    var charactersPage = 1

    private val _newCharacters = MutableLiveData<Characters>()
    val newCharacters: LiveData<Characters> = _newCharacters

    init {
        getAllCharacters(charactersPage)
    }

    private fun getAllCharacters(page: Int) {
        val disposable = getCharactersUseCase(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ characters ->
                val old = _newCharacters.value
                val new = old?.results.orEmpty() + characters?.results.orEmpty()
                _newCharacters.value = Characters(null, new)
            }, {
                Log.e("TAG","не удалось получить всех персонажей")
            })

        compositeDisposable.add(disposable)
    }

    fun nextPage() {
        charactersPage += 1
        getAllCharacters(charactersPage)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}