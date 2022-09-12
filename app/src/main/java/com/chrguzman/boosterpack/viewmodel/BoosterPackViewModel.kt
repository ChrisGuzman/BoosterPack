package com.chrguzman.boosterpack.viewmodel

import androidx.lifecycle.*
import com.chrguzman.boosterpack.networking.BoosterResponse
import com.chrguzman.boosterpack.networking.Resource
import com.chrguzman.boosterpack.repo.CardRepositoryInterface
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoosterPackViewModel @Inject constructor(
    private val repository: CardRepositoryInterface,
) : ViewModel() {
    private val boosterCards = MutableLiveData<Resource<BoosterResponse>>(
        Resource.Init()
    )
    val boosterCardList: LiveData<Resource<BoosterResponse>>
        get() = boosterCards


    fun openBooster(setCode: String?) {
        if (setCode.isNullOrEmpty()) {
            boosterCards.value = Resource.Success(BoosterResponse(emptyList()))
        } else {
            viewModelScope.launch {
                boosterCards.value = Resource.Loading()
                val response = repository.openBooster(setCode)
                boosterCards.postValue(response)
            }
        }
    }
}