package com.github.slznvk.domain

import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Tickets
import com.github.slznvk.domain.dto.TicketsOffers


interface TicketsRepository {
    suspend fun loadOffers(): List<Offer>
    suspend fun loadTicketsOffers(): TicketsOffers
    suspend fun loadTickets(): Tickets
}