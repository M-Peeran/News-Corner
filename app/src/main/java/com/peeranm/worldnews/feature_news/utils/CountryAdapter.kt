package com.peeranm.worldnews.feature_news.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.peeranm.worldnews.databinding.CountryItemBinding

class CountryAdapter(listener: OnCheckChangeListener<CountryCode>
) : RecyclerView.Adapter<CountryAdapter.CountryItemHolder>() {

    private var listener: OnCheckChangeListener<CountryCode>? = null

    private val countryCodes = CountryCode.values()

    init { this.listener = listener }

    inner class CountryItemHolder(private val binding: CountryItemBinding)
        : RecyclerView.ViewHolder(binding.root), CompoundButton.OnCheckedChangeListener {

        init { binding.radBtnSelectCountry.setOnCheckedChangeListener(this) }

        fun bind(position: Int) {
            val countryCode = countryCodes[position]
            binding.textCountryName.text = countryCode.country
        }

        override fun onCheckedChanged(compButton: CompoundButton?, isSelected: Boolean) {
            listener?.onCheckChange(
                compButton = compButton,
                data = countryCodes[bindingAdapterPosition],
                isSelected = isSelected,
                position = bindingAdapterPosition
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemHolder {
        return CountryItemHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountryItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = countryCodes.size

    fun onClear() {
        this.listener = null
    }
}