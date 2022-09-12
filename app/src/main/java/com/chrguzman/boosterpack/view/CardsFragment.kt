package com.chrguzman.boosterpack.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.chrguzman.boosterpack.R
import com.chrguzman.boosterpack.adapter.CardAdapter
import com.chrguzman.boosterpack.databinding.FragmentCardsBinding
import com.chrguzman.boosterpack.networking.Resource
import com.chrguzman.boosterpack.viewmodel.BoosterPackViewModel
import com.chrguzman.boosterpack.viewmodel.CardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CardsFragment: Fragment(R.layout.fragment_cards) {

    lateinit var cardAdapter: CardAdapter
    private var fragmentBinding: FragmentCardsBinding? = null

    private val cardViewModel by viewModels<CardViewModel>()
    private val boosterPackViewModel by viewModels<BoosterPackViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCardsBinding.bind(view)
        fragmentBinding = binding

        displayBoosterCards()
        cardAdapter = CardAdapter {
            cardViewModel.saveCard(it)
        }
        val setCodeList: Array<String> = resources.getStringArray(R.array.set_codes)
        val setCodeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, setCodeList)
        binding.setCodeSearch.threshold = 1
        binding.setCodeSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.setCodeSearch.showDropDown()
            }
        }
        binding.setCodeSearch.setOnClickListener {
            binding.setCodeSearch.showDropDown()
        }
        binding.setCodeSearch.setAdapter(setCodeAdapter)

        binding.setCodeSearch.setOnItemClickListener { parent, itemView, position, id ->
            boosterPackViewModel.openBooster(setCodeList[position])
        }

        binding.cardsRv.adapter = cardAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.cardsRv.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.cardsRv.context,
            layoutManager.orientation
        )
        binding.cardsRv.addItemDecoration(dividerItemDecoration)
        binding.fab.setOnClickListener {
            boosterPackViewModel.openBooster(setCodeList.random())
        }
    }

    private fun displayBoosterCards() {
        boosterPackViewModel.boosterCardList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    fragmentBinding?.welcomeMessage?.visibility = View.VISIBLE
                    fragmentBinding?.cardsRv?.visibility = View.GONE
                }
                is Resource.Loading -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                    fragmentBinding?.cardsRv?.visibility = View.VISIBLE
                    fragmentBinding?.welcomeMessage?.visibility = View.GONE
                }
                is Resource.Success -> {
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    fragmentBinding?.cardsRv?.visibility = View.VISIBLE
                    cardAdapter.cards = it.data?.cards.orEmpty()
                    fragmentBinding?.welcomeMessage?.visibility = View.GONE
                }
                is Resource.Init -> {
                    fragmentBinding?.progressBar?.visibility = View.GONE
                    fragmentBinding?.welcomeMessage?.visibility = View.VISIBLE
                    fragmentBinding?.cardsRv?.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}