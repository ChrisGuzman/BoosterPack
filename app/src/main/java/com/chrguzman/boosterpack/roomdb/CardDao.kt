package com.chrguzman.boosterpack.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chrguzman.boosterpack.model.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * from cards")
    fun observeArts(): LiveData<List<Card>>
}