package com.github.slznvk.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.slznvk.domain.TicketsRepository
import com.github.slznvk.domain.dto.Offer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TicketsRepository
) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>>
        get() = _offers

    fun load() {
        viewModelScope.launch {
            try {
                _offers.postValue(repository.loadOffers())
            } catch (e: Exception) {

            }

        }
    }
}