package com.github.slznvk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.FragmentAllTicketsBinding
import com.github.slznvk.presentation.ui.CountrySelectedFragment.Companion.DATE
import com.github.slznvk.presentation.ui.MainFragment.Companion.ARRIVAL
import com.github.slznvk.presentation.ui.MainFragment.Companion.DEPARTURE


class AllTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAllTicketsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTicketsBinding.inflate(layoutInflater, container, false)

        val departure = arguments?.getString(DEPARTURE)
        val arrival = arguments?.getString(ARRIVAL)
        val date = arguments?.getString(DATE)

        binding.apply {
            pickedPlaces.text = getString(R.string.picked_places_format, departure, arrival)
            options.text = getString(R.string.passenger_info_format, date)

            iconBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }


        return binding.root
    }
}