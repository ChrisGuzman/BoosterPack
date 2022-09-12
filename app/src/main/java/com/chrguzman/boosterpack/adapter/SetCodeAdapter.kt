package com.chrguzman.boosterpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chrguzman.boosterpack.R

class SetCodeAdapter(private val onClick: (String) -> Unit) : ListAdapter<String, SetCodeAdapter.SetCodeViewHolder>(SetCodeDiffCallback) {

    class SetCodeViewHolder(itemView: View, val onClick: (String) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val setCode: TextView = itemView.findViewById(R.id.set_code)
        private var currentCode: String? = null

        init {
            itemView.setOnClickListener {
                currentCode?.let {
                    onClick(it)
                }
            }
        }

        fun bind(code: String) {
            currentCode = code
            setCode.text = code
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetCodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_set_code, parent, false)
        return SetCodeViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SetCodeViewHolder, position: Int) {
        val setCode = getItem(position)
        holder.bind(setCode)
    }
}

object SetCodeDiffCallback :DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}