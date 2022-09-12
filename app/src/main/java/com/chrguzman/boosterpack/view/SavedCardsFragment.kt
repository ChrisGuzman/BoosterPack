package com.chrguzman.boosterpack.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.chrguzman.boosterpack.R
import com.chrguzman.boosterpack.adapter.CardAdapter
import com.chrguzman.boosterpack.databinding.FragmentSavedCardsBinding
import com.chrguzman.boosterpack.viewmodel.CardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SavedCardsFragment: Fragment(R.layout.fragment_saved_cards) {

    lateinit var cardAdapter: CardAdapter
    private var fragmentBinding: FragmentSavedCardsBinding? = null

    val cardViewModel by activityViewModels<CardViewModel>()

    private val savedCardsSwipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedCard = cardAdapter.cards[layoutPosition]
            cardViewModel.deleteCard(selectedCard)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSavedCardsBinding.bind(view)
        fragmentBinding = binding
        displaySavedCards()
        ItemTouchHelper(savedCardsSwipeCallback).attachToRecyclerView(binding.savedCardsRv)
        cardAdapter = CardAdapter {
            cardViewModel.deleteCard(it)
        }
        binding.savedCardsRv.adapter = cardAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.savedCardsRv.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.savedCardsRv.context,
            layoutManager.orientation
        )
        binding.savedCardsRv.addItemDecoration(dividerItemDecoration)
    }

    private fun displaySavedCards() {
        cardViewModel.savedCardsList.observe(viewLifecycleOwner) {
            cardAdapter.cards = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}