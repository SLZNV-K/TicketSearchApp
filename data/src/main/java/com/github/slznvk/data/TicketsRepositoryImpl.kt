package com.github.slznvk.data

import com.github.slznvk.domain.ApiError
import com.github.slznvk.domain.NetworkError
import com.github.slznvk.domain.TicketsRepository
import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Ticket
import com.github.slznvk.domain.dto.TicketOffer
import java.io.IOException
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val service: ApiService
) : TicketsRepository {

    override suspend fun loadOffers(): List<Offer> {
        return try {
            val response = service.loadOffers()

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())

            }
            response.body()?.offers ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun loadTicketsOffers(): List<TicketOffer> {
        return try {
            val response = service.loadTicketsOffers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            println("RESPONSE BODY: ${response.body()}")
            response.body()?.ticketOffers ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun loadTickets(): List<Ticket> {
        return try {
            val response = service.loadTickets()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            response.body()?.tickets ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

}