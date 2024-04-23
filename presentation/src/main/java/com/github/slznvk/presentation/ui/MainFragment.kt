package com.github.slznvk.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.adapter.Delegates.offersDelegate
import com.github.slznvk.presentation.databinding.FragmentMainBinding
import com.github.slznvk.presentation.viewModel.MainViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val adapter = ListDelegationAdapter(
        offersDelegate()
    )

    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding.placeOfDeparture.setText(sharedPreferences.getString(INPUT_KEY, ""))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadOffers()

        with(binding) {
            placeOfArrival.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    findNavController()
                        .navigate(
                            R.id.action_mainFragment_to_searchFragment,
                            Bundle().apply {
                                putString(DEPARTURE, placeOfDeparture.text.toString())
                                putString(ARRIVAL, placeOfArrival.text.toString())
                            }
                        )
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            recycler.adapter = adapter
        }

        viewModel.offers.observe(viewLifecycleOwner) {
            binding.recycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter.items = it
        }

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.errorLoadingText.isVisible = state.error
            binding.retryButton.isVisible = state.error
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadOffers()
        }
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.edit().putString(INPUT_KEY, binding.placeOfDeparture.text.toString())
            .apply()
    }

    companion object {
        const val DEPARTURE = "placeOfDeparture"
        const val ARRIVAL = "placeOfArrival"
        const val INPUT_KEY = "user_input"
    }
}