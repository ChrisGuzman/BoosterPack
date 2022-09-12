package com.chrguzman.boosterpack.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.chrguzman.boosterpack.networking.BoosterResponse
import com.chrguzman.boosterpack.networking.Resource
import com.chrguzman.boosterpack.networking.MtgApi
import com.chrguzman.boosterpack.model.Card
import com.chrguzman.boosterpack.roomdb.CardDao
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val cardDao: CardDao,
    private val retrofitApi: MtgApi
): CardRepositoryInterface {
    override suspend fun saveCard(card: Card) {
        cardDao.saveCard(card)
    }

    override suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card)
    }

    override fun getSavedCards(): LiveData<List<Card>> {
        return cardDao.observeArts()
    }

    override suspend fun openBooster(setCode: String): Resource<BoosterResponse> {
        return try {
            val response = retrofitApi.getBooster(setCode)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Response was empty")
            } else {
                Resource.Error("Response did not succeed")
            }
        } catch (e: Exception) {
            Log.e("TAG", "openBooster: ", e)
            Resource.Error("Network error")
        }

    }
}