package com.github.slznvk.data

import com.github.slznvk.domain.ApiError
import com.github.slznvk.domain.NetworkError
import com.github.slznvk.domain.Offers
import com.github.slznvk.domain.Tickets
import com.github.slznvk.domain.TicketsOffers
import com.github.slznvk.domain.TicketsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TicketsRepositoryImpl(
    private val service: ApiService
) : TicketsRepository {

    constructor() : this(
        Retrofit.Builder().baseUrl("https://drive.google.com/file/d/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    )

    override suspend fun loadOffers(): Offers {
        return try {
            val response = service.loadOffers()
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