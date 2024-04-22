package com.github.slznvk.data

import com.github.slznvk.domain.ApiError
import com.github.slznvk.domain.NetworkError
import com.github.slznvk.domain.TicketsRepository
import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Tickets
import com.github.slznvk.domain.dto.TicketsOffers
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

    override suspend fun loadTicketsOffers(): TicketsOffers {
        return try {
            val response = service.loadTicketsOffers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    override suspend fun loadTickets(): Tickets {
        return try {
            val response = service.loadTickets()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

}