package com.chrguzman.boosterpack.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"], unique = true)],
    tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val localId: Int? = null,
    val name: String,
    val manaCost: String?,
    val type: String,
    val text: String,
    val rarity: String,
    val set: String,
    val power: String?,
    val toughness: String?,
    val loyalty: String?
) {
    val imageUrl: String
        get() = "https://source.unsplash.com/random/?$name&orientation=landscape"
}