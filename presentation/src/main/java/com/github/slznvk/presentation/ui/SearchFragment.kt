package com.github.slznvk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.FragmentSearchBinding
import com.github.slznvk.presentation.ui.MainFragment.Companion.ARRIVAL
import com.github.slznvk.presentation.ui.MainFragment.Companion.DEPARTURE

class SearchFragment : DialogFragment() {
    private lateinit var binding: FragmentSearchBinding


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as? MainActivity
        mainActivity?.let { activity ->
            activity.binding.bottomNavigationView.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        val mainActivity = activity as? MainActivity
        mainActivity?.let { activity ->
            activity.binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin_top)
        view.layoutParams = layoutParams

        val departure = arguments?.getString(DEPARTURE)
        val arrival = arguments?.getString(ARRIVAL)

        binding.apply {
            placeOfDeparture.setText(departure)
            placeOfArrival.setText(arrival)
            placeOfArrival.requestFocus()

            placeOfArrival.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (placeOfArrival.text.isNotBlank() && placeOfDeparture.text.isNotBlank()) {
                        findNavController().navigate(
                            R.id.action_searchFragment_to_countrySelectedFragment,
                            Bundle().apply {
                                clear()
                                putString(DEPARTURE, placeOfDeparture.text.toString())
                                putString(ARRIVAL, placeOfArrival.text.toString())
                            })
                        true
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.the_fields_must_be_filled_in),
                            Toast.LENGTH_LONG
                        ).show()
                        false
                    }

                } else {
                    false
                }
            }


            // альтернатива переходу по кнопке "ВВОД" на клавиатуре, если производители иначе определили кнопку ввода
            iconSearch.setOnClickListener {
                if (placeOfArrival.text.isNotBlank() && placeOfDeparture.text.isNotBlank()) {
                    findNavController().navigate(
                        R.id.action_searchFragment_to_countrySelectedFragment,
                        Bundle().apply {
                            clear()
                            putString(DEPARTURE, placeOfDeparture.text.toString())
                            putString(ARRIVAL, placeOfArrival.text.toString())
                        })
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.the_fields_must_be_filled_in),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            routeButton.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_plugFragment)
            }
            anywhereButton.setOnClickListener {
                placeOfArrival.setText(R.string.anywhere)
            }
            weekendButton.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_plugFragment)
            }



            fireButton.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_plugFragment)
            }

            istanbulContainer.setOnClickListener {
                placeOfArrival.setText(R.string.istanbul)
            }

            sochiContainer.setOnClickListener {
                placeOfArrival.setText(R.string.sochi)
            }

            phuketContainer.setOnClickListener {
                placeOfArrival.setText(R.string.phuket)
            }
            clearText.setOnClickListener {
                placeOfArrival.setText("")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.mainFragment)
                }
            })

        return view
    }
}