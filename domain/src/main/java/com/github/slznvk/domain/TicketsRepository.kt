package com.github.slznvk.domain

import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Ticket
import com.github.slznvk.domain.dto.TicketOffer


interface TicketsRepository {
    suspend fun loadOffers(): List<Offer>
    suspend fun loadTicketsOffers(): List<TicketOffer>
    suspend fun loadTickets(): List<Ticket>
}