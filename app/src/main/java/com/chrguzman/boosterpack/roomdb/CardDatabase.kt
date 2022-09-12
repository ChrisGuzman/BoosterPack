package com.chrguzman.boosterpack.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chrguzman.boosterpack.model.Card

@Database(entities = [Card::class], version = 4)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}