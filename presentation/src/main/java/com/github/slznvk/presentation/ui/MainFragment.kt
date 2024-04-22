package com.github.slznvk.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.adapter.Adapter
import com.github.slznvk.presentation.databinding.FragmentMainBinding
import com.github.slznvk.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter

    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        viewModel.load()

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
        }

        viewModel.offers.observe(viewLifecycleOwner) {
            println(it)
            binding.recycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = Adapter(it)
            binding.recycler.adapter = adapter
            adapter.items = it
        }

        return binding.root
    }

    companion object {
        const val DEPARTURE = "placeOfDeparture"
        const val ARRIVAL = "placeOfArrival"
    }
}