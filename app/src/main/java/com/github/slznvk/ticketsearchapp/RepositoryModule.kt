package com.github.slznvk.ticketsearchapp

import com.github.slznvk.data.TicketsRepositoryImpl
import com.github.slznvk.domain.TicketsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsPostRepository(impl: TicketsRepositoryImpl): TicketsRepository
}