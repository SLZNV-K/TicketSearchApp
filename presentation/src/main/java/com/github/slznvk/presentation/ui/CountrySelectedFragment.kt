package com.github.slznvk.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.FragmentCountrySelectedBinding
import com.github.slznvk.presentation.ui.MainFragment.Companion.ARRIVAL
import com.github.slznvk.presentation.ui.MainFragment.Companion.DEPARTURE
import java.util.Calendar
import java.util.Date


class CountrySelectedFragment : Fragment() {
    private lateinit var binding: FragmentCountrySelectedBinding
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountrySelectedBinding.inflate(layoutInflater, container, false)

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

            allTicketsButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_countrySelectedFragment_to_allTicketsFragment,
                    Bundle().apply {
                        clear()
                        putString(DEPARTURE, placeOfDeparture.text.toString())
                        putString(ARRIVAL, placeOfArrival.text.toString())
                        putString(DATE, todayDate.text.toString())
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
        }


        return binding.root
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

    companion object{
        const val DATE = "DATE"
    }

}