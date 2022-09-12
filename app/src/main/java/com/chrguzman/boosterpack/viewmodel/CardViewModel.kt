package com.chrguzman.boosterpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrguzman.boosterpack.repo.CardRepositoryInterface
import com.chrguzman.boosterpack.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepositoryInterface
) : ViewModel() {
    val savedCardsList = repository.getSavedCards()

    fun deleteCard(card: Card) = viewModelScope.launch {
        repository.deleteCard(card)
    }

    fun saveCard(card: Card) = viewModelScope.launch {
        repository.saveCard(card)
    }
}