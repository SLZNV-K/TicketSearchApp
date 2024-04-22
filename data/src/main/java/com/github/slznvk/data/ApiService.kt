package com.github.slznvk.data

import com.github.slznvk.domain.dto.Offers
import com.github.slznvk.domain.dto.Tickets
import com.github.slznvk.domain.dto.TicketsOffers
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav")
    suspend fun loadOffers(): Response<Offers>

    @GET("uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta")
    suspend fun loadTicketsOffers(): Response<TicketsOffers>

    @GET("uc?id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    suspend fun loadTickets(): Response<Tickets>
}