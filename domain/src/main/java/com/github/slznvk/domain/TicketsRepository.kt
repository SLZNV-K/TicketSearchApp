package com.github.slznvk.domain


interface TicketsRepository {
    suspend fun loadOffers(): Offers
    suspend fun loadTicketsOffers(): TicketsOffers
    suspend fun loadTickets(): Tickets
}