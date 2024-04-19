package com.github.slznvk.data

import com.github.slznvk.domain.Offers
import com.github.slznvk.domain.Tickets
import com.github.slznvk.domain.TicketsOffers
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    //https://drive.google.com/file/d/
    @GET("1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav/view?usp=sharing")
    suspend fun loadOffers(): Response<Offers>

    @GET("13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta/view?usp=sharing")
    suspend fun loadTicketsOffers(): Response<TicketsOffers>

    @GET("1HogOsz4hWkRwco4kud3isZHFQLUAwNBA/view?usp=sharing")
    suspend fun loadTickets(): Response<Tickets>
}