package com.github.slznvk.presentation.adapter

import com.github.slznvk.domain.dto.Offer
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class Adapter(items: List<Offer>) : ListDelegationAdapter<List<Offer>>() {
    init {
        delegatesManager.addDelegate(OffersDelegate())
    }
}