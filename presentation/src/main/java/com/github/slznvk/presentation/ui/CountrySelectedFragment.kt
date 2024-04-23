package com.github.slznvk.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.adapter.Delegates.offersTicketsDelegate
import com.github.slznvk.presentation.databinding.FragmentCountrySelectedBinding
import com.github.slznvk.presentation.ui.MainFragment.Companion.ARRIVAL
import com.github.slznvk.presentation.ui.MainFragment.Companion.DEPARTURE
import com.github.slznvk.presentation.viewModel.MainViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class CountrySelectedFragment : Fragment() {

    private lateinit var binding: FragmentCountrySelectedBinding

    private val viewModel: MainViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    private val adapter = ListDelegationAdapter(
        offersTicketsDelegate()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountrySelectedBinding.inflate(layoutInflater, container, false)

        viewModel.loadTicketsOffers()

        val departure = arguments?.getString(DEPARTURE)
        val arrival = arguments?.getString(ARRIVAL)

        binding.apply {
            placeOfDeparture.setText(departure)
            placeOfArrival.setText(arrival)

            returnDate.setOnClickListener {
                showDatePickerDialog(it)
            }

            todayDate.text =
                "${calendar.get(Calendar.DAY_OF_MONTH)} ${getMonthName(calendar.get(Calendar.MONTH))}, ${getDayOfWeek()}"

            todayDate.setOnClickListener {
                showDatePickerDialog(it)
            }

            recycler.adapter = adapter

            viewModel.ticketOffers.observe(viewLifecycleOwner) {
                recycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter.items = it
            }

            viewModel.dataState.observe(viewLifecycleOwner) { state ->
                errorLoadingText.isVisible = state.error
                retryButton.isVisible = state.error
            }

            retryButton.setOnClickListener {
                viewModel.loadOffers()
            }

            allTicketsButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_countrySelectedFragment_to_allTicketsFragment,
                    Bundle().apply {
                        clear()
                        putString(DEPARTURE, placeOfDeparture.text.toString())
                        putString(ARRIVAL, placeOfArrival.text.toString())
                        putString(DATE, convertDateString(todayDate.text.toString()))
                    }
                )
            }

            swapButtom.setOnClickListener {
                val swapText = placeOfArrival.text
                placeOfArrival.text = placeOfDeparture.text
                placeOfDeparture.text = swapText
            }

            clearText.setOnClickListener {
                placeOfArrival.setText("")
            }

            iconBack.setOnClickListener {
                findNavController().navigateUp()
            }



            return root
        }
    }

    private fun showDatePickerDialog(view: View) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate =
                    "$selectedDay ${getMonthName(selectedMonth)}, ${
                        getDayOfWeek(
                            Calendar.getInstance().apply {
                                set(Calendar.YEAR, selectedYear)
                                set(Calendar.MONTH, selectedMonth)
                                set(Calendar.DAY_OF_MONTH, selectedDay)
                            }.time
                        )
                    }"
                (view as Button).text = formattedDate
            }, year, month, dayOfMonth
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun getDayOfWeek(date: Date? = null): String {
        val calendar = Calendar.getInstance()
        date?.let { calendar.time = it }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysOfWeek = arrayOf(
            "вс",
            "пн",
            "вт",
            "ср",
            "чт",
            "пт",
            "сб"
        )
        return daysOfWeek[dayOfWeek - 1]
    }

    private fun getMonthName(month: Int): String {
        val monthNames = arrayOf(
            "янв", "февр", "март", "апр", "май", "июнь",
            "июль", "авг", "сент", "окт", "нояб", "дек"
        )
        return monthNames[month]
    }

    private fun convertDateString(input: String): String {
        val monthMap = mapOf(
            "янв" to "января",
            "фпр" to "февраля",
            "мар" to "марта",
            "апр" to "апреля",
            "май" to "мая",
            "июн" to "июня",
            "июл" to "июля",
            "авг" to "августа",
            "сен" to "сентября",
            "окт" to "октября",
            "ноя" to "ноября",
            "дек" to "декабря"
        )

        val parts = input.split(",")
        if (parts.size != 2) {
            return "Неверный формат даты"
        }

        val day = parts[0].split(" ")[0].toIntOrNull() ?: return "Неверный формат числа"
        val monthAbbreviation = parts[0].split(" ")[1]
        val month = monthMap[monthAbbreviation] ?: return "Неизвестный месяц"

        return "$day $month"
    }

    companion object {
        const val DATE = "DATE"
    }

}