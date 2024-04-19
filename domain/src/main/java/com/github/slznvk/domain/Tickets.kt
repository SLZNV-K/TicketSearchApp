package com.github.slznvk.domain

data class Ticket(
    val id: Int,
    val badge: String?,
    val price: Price,
    val providerName: String,
    val company: String,
    val departure: Departure,
    val arrival: Arrival,
    val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: Luggage?,
    val handLuggage: HandLuggage?,
    val isReturnable: Boolean,
    val isExchangable: Boolean
)

data class Tickets(val tickets: List<Ticket>)
