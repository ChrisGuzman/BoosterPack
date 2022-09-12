package com.chrguzman.boosterpack.repo

import androidx.lifecycle.LiveData
import com.chrguzman.boosterpack.networking.BoosterResponse
import com.chrguzman.boosterpack.networking.Resource
import com.chrguzman.boosterpack.model.Card

interface CardRepositoryInterface {
    suspend fun saveCard(card: Card)

    suspend fun deleteCard(card: Card)

    fun getSavedCards(): LiveData<List<Card>>

    suspend fun openBooster(setCode: String): Resource<BoosterResponse>
}