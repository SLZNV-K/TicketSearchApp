package com.github.slznvk.domain.dto

import com.google.gson.annotations.SerializedName


data class TicketOffer(
    val id: Long,
    val title: String,
    @SerializedName("time_range") val timeRange: List<String>,
    val price: Price
) : ListItem


//data class TicketsOffers(val ticketsOffers: List<TicketOffer>) : ListItem

data class TicketsOffers(
    @SerializedName("tickets_offers") val ticketOffers: List<TicketOffer>
) : ListItem