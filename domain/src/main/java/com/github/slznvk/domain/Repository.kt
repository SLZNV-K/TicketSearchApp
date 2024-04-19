package com.github.slznvk.domain


interface Repository {

    suspend fun loadOffers(): Offers

    suspend fun loadTicketsOffers(): TicketsOffers

    suspend fun loadTickets(): Tickets
}