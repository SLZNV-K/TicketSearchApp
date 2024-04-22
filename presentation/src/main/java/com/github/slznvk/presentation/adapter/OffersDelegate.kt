package com.github.slznvk.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.slznvk.domain.dto.Offer
import com.github.slznvk.presentation.R
import com.github.slznvk.presentation.databinding.CardOffersBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class OffersDelegate : AdapterDelegate<List<Offer>>() {

    override fun isForViewType(items: List<Offer>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = CardOffersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OffersViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<Offer>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val offer = items[position]
        val viewHolder = holder as OffersViewHolder
        viewHolder.bind(offer)
    }

    private class OffersViewHolder(private val binding: CardOffersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            binding.apply {
                when (offer.id) {
                    1 -> image.setImageResource(R.drawable.offers_id1)
                    2 -> image.setImageResource(R.drawable.offers_id2)
                    else -> image.setImageResource(R.drawable.offers_id3)
                }
                title.text = offer.title
                town.text = offer.town
                price.text = offer.price.value.toString()
            }
        }
    }
}
