package com.chrguzman.boosterpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.chrguzman.boosterpack.R
import com.chrguzman.boosterpack.model.asManaCostSpan
import com.chrguzman.boosterpack.model.asTextSpan
import com.chrguzman.boosterpack.model.Card
import javax.inject.Inject

class CardAdapter(private val onLongPress: (Card) -> Unit) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    @Inject
    lateinit var requestManager: RequestManager

    private val diffUtil = object : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var cards: List<Card>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view, onLongPress)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int = cards.size

    class CardViewHolder(itemView: View, val onLongPress: (Card) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val cardImage = itemView.findViewById<ImageView>(R.id.card_image)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val manaCost = itemView.findViewById<TextView>(R.id.mana_cost)
        private val type = itemView.findViewById<TextView>(R.id.type)
        private val text = itemView.findViewById<TextView>(R.id.text)
        private val rarity = itemView.findViewById<TextView>(R.id.rarity)
        private val set = itemView.findViewById<TextView>(R.id.set)
        private val loyalty = itemView.findViewById<TextView>(R.id.loyalty)
        private val power = itemView.findViewById<TextView>(R.id.power)
        private val toughness = itemView.findViewById<TextView>(R.id.toughness)
        private val creaturePowerToughness = itemView.findViewById<Group>(R.id.creature_power_toughness)
        private var currentCard: Card? = null

        init {
            itemView.setOnClickListener {
                currentCard?.let {
                    onLongPress(it)
                }
            }
        }

        fun bind(card: Card) {

            Glide
                .with(itemView.context)
                .load(card.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.planeswalker)
                .error(R.drawable.planeswalker)
                .into(cardImage)
            currentCard = card
            name.text = card.name
            manaCost.text = card.manaCost?.asManaCostSpan(itemView.context)
            type.text = card.type
            text.text = card.text.asTextSpan(itemView.context)
            rarity.text = card.rarity
            set.text = card.set
            if (card.power.isNullOrEmpty().not() || card.toughness.isNullOrEmpty().not()) {
                loyalty.visibility = View.GONE
                power.text = card.power
                toughness.text = card.toughness
                creaturePowerToughness.visibility = View.VISIBLE
            } else if (card.loyalty.isNullOrEmpty().not()) {
                creaturePowerToughness.visibility = View.GONE
                loyalty.visibility = View.VISIBLE
                loyalty.text = card.loyalty
            } else {
                loyalty.visibility = View.GONE
                creaturePowerToughness.visibility = View.GONE
            }
        }
    }
}