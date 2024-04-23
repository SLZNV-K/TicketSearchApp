package com.github.slznvk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.adapter.Delegates.ticketsDelegate
import com.github.slznvk.presentation.databinding.FragmentAllTicketsBinding
import com.github.slznvk.presentation.ui.CountrySelectedFragment.Companion.DATE
import com.github.slznvk.presentation.ui.MainFragment.Companion.ARRIVAL
import com.github.slznvk.presentation.ui.MainFragment.Companion.DEPARTURE
import com.github.slznvk.presentation.viewModel.MainViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAllTicketsBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter = ListDelegationAdapter(
        ticketsDelegate()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTicketsBinding.inflate(layoutInflater, container, false)

        val departure = arguments?.getString(DEPARTURE)
        val arrival = arguments?.getString(ARRIVAL)
        val date = arguments?.getString(DATE)

        viewModel.loadTickets()

        binding.apply {
            pickedPlaces.text = getString(R.string.picked_places_format, departure, arrival)
            options.text = getString(R.string.passenger_info_format, date)

            iconBack.setOnClickListener {
                findNavController().navigateUp()
            }

            recycler.adapter = adapter

            viewModel.tickets.observe(viewLifecycleOwner) {
                recycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter.items = it
            }

            viewModel.dataState.observe(viewLifecycleOwner) {
                errorLoadingText.isVisible = it.error
                retryButton.isVisible = it.error
            }

            retryButton.setOnClickListener {
                viewModel.loadTickets()
            }

            return root
        }
    }
}