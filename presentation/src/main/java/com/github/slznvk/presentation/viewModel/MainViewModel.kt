package com.github.slznvk.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.slznvk.domain.TicketsRepository
import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.domain.dto.Ticket
import com.github.slznvk.domain.dto.TicketOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TicketsRepository
) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    private val _ticketOffers = MutableLiveData<List<TicketOffer>>()
    private val _tickets = MutableLiveData<List<Ticket>>()

    private val _dataState = MutableLiveData<State>()
    val dataState: LiveData<State>
        get() = _dataState
    val offers: LiveData<List<Offer>>
        get() = _offers

    val ticketOffers: LiveData<List<TicketOffer>>
        get() = _ticketOffers

    val tickets: LiveData<List<Ticket>>
        get() = _tickets

    fun loadOffers() {
        viewModelScope.launch {
            _dataState.value = State(loading = true)
            try {
                _offers.postValue(repository.loadOffers())
                _dataState.value = State()
            } catch (e: Exception) {
                _dataState.value = State(error = true)
            }

        }
    }

    fun loadTicketsOffers() {
        viewModelScope.launch {
            _dataState.value = State(loading = true)
            try {
                println("VIEW_MODEL: ${repository.loadTicketsOffers()}")
                _ticketOffers.postValue(repository.loadTicketsOffers())
                _dataState.value = State()
            } catch (e: Exception) {
                _dataState.value = State(error = true)
            }
        }
    }

    fun loadTickets() {
        viewModelScope.launch {
            _dataState.value = State(loading = true)
            try {
                _tickets.postValue(repository.loadTickets())
                _dataState.value = State()
            } catch (e: Exception) {
                _dataState.value = State(error = true)
            }
        }
    }
}