package com.github.slznvk.presentation.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.slznvk.domain.dto.ListItem
import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Ticket
import com.github.slznvk.domain.dto.TicketOffer
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.CardOffersBinding
import com.github.slznvk.presentation.databinding.CardTicketFullBinding
import com.github.slznvk.presentation.databinding.CardTicletPreviewBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

object Delegates {

    fun offersDelegate() = adapterDelegateViewBinding<Offer, ListItem, CardOffersBinding>(
        { layoutInflater, root -> CardOffersBinding.inflate(layoutInflater, root, false) }
    ) {

        bind {
            when (item.id) {
                1 -> binding.image.setImageResource(R.drawable.offers_id1)
                2 -> binding.image.setImageResource(R.drawable.offers_id2)
                else -> binding.image.setImageResource(R.drawable.offers_id3)
            }
            binding.title.text = item.title
            binding.town.text = item.town
            binding.price.text = formatPriceWithSpaces(item.price.value)
        }
    }

    fun offersTicketsDelegate() =
        adapterDelegateViewBinding<TicketOffer, ListItem, CardTicletPreviewBinding>(
            { layoutInflater, root ->
                CardTicletPreviewBinding.inflate(
                    layoutInflater,
                    root,
                    false
                )
            }
        ) {

            bind {
                binding.title.text = item.title
                binding.price.text = formatPriceWithSpaces(item.price.value)
                binding.timeRange.text = item.timeRange.joinToString(" ")
                when (item.id) {
                    1L -> binding.circle.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))

                    10L -> binding.circle.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue))

                    else -> binding.circle.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                }
            }
        }

    fun ticketsDelegate() = adapterDelegateViewBinding<Ticket, ListItem, CardTicketFullBinding>(
        { layoutInflater, root ->
            CardTicketFullBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }
    ) {

        bind {
            if (item.badge != null) {
                binding.badgeContainer.isVisible = true
                binding.badge.text = item.badge.toString()
            } else {
                binding.badgeContainer.isVisible = false
            }
            binding.price.text = formatPriceWithSpaces(item.price.value)

            binding.departureDate.text = timeFromTheDate(item.departure.date)
            binding.departureAirport.text = item.departure.airport

            binding.arrivalDate.text = timeFromTheDate(item.arrival.date)
            binding.arrivalAirport.text = item.arrival.airport

            if (!item.hasTransfer) {
                binding.flightTime.text = getString(
                    R.string.flight_time_format,
                    calculateTimeDifference(item.departure.date, item.arrival.date)
                )
            } else {
                binding.flightTime.text =
                    calculateTimeDifference(item.departure.date, item.arrival.date)
            }
        }
    }

    private fun formatPriceWithSpaces(number: Long): String {
        val formattedNumber = String.format("%,d", number)
        return formattedNumber.replace(",", " ")
    }

    private fun timeFromTheDate(date: String): String =
        date.split("T")[1].split(":").subList(0, 2).joinToString(":")

    private fun formatDecimalToNearestHalf(number: Double): String {
        val roundedNumber = number.roundToInt()
        val fraction = (number - roundedNumber).absoluteValue
        return if (fraction == 0.0) {
            roundedNumber.toString().split(".")[0]
        } else {
            "$roundedNumber${if (fraction < 0.5) " ч" else ".5 ч"}"
        }
    }

    private fun calculateTimeDifference(startTime: String, endTime: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val startDate = formatter.parse(startTime)
        val endDate = formatter.parse(endTime)

        val diffInMinutes = (endDate!!.time - startDate!!.time) / (1000 * 60)

        val hours = diffInMinutes / 60
        val minutes = diffInMinutes % 60
        val fractionalHours = hours + minutes / 60.0

        return formatDecimalToNearestHalf(fractionalHours)
    }
}
