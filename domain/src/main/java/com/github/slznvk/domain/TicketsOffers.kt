package com.github.slznvk.domain

data class TicketsOffers(
    val id: Long,
    val title: String,
    val timeRange: List<String>,
    val price: Price
)