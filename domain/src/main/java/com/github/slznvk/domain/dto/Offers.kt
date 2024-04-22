package com.github.slznvk.domain.dto

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price,
)

data class Offers(val offers: List<Offer>)

