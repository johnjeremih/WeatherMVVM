package com.john.wathermvvm.view.adapters.city

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.wathermvvm.R
import com.john.wathermvvm.databinding.AdapterCityItemBinding
import com.john.wathermvvm.model.City
import com.john.wathermvvm.util.TimeAgoFormatter
import com.squareup.picasso.Picasso

class CityViewHolder(private val binding: AdapterCityItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val format: TimeAgoFormatter = TimeAgoFormatter()

    fun bind(city: City) {
        var formattedDate = format.format(city.lastUpdate)
        if (formattedDate.contains("moments")) {
            formattedDate = "1m"
        }

        val imageUrl = getUrl(city.weather?.icon.toString(), binding.root.resources)
        Picasso.get().load(imageUrl).into(binding.adapterCityIcon)
        binding.adapterCityName.text = city.cityName
        binding.adapterCityTemperature.text = city.getTempString()
        binding.adapterCityTime.text = formattedDate
        binding.adapterCityDescription.text = city.weather?.description ?: ""
    }
    companion object {
        fun create(parent: ViewGroup): CityViewHolder {
            val binding =
                AdapterCityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CityViewHolder(binding)
        }
    }
    private fun getUrl(icon: String, res: Resources): String {
        return java.lang.String.format(res.getString(R.string.image_holder), icon)
    }
}
