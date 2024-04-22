package com.github.slznvk.domain.dto

data class TicketsOffers(
    val id: Long,
    val title: String,
    val timeRange: List<String>,
    val price: Price
)