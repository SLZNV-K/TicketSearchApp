package com.github.slznvk.domain.dto

data class TicketOffer(
    val id: Long,
    val title: String,
    val timeRange: List<String>,
    val price: Price
) : ListItem

data class TicketsOffers(val ticketOffers: List<TicketOffer>) : ListItem