package com.john.weathermvvm.view.adapters.forecast

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.weathermvvm.R
import com.john.weathermvvm.databinding.AdapterForecastItemBinding
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.util.TimeAgoFormatter
import com.squareup.picasso.Picasso

class ForecastViewHolder(private val binding: AdapterForecastItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val format: TimeAgoFormatter = TimeAgoFormatter()

    fun bind(forecast: Forecast){
        val formattedTime = format.format(forecast.dateTime)

        val imageUrl = getUrl(forecast.weather?.icon.toString(),binding.root.resources)
        Picasso.get()
            .load(imageUrl)
            .into(binding.adapterForecastIcon)

        binding.adapterForecastTime.text = formattedTime
        binding.adapterForecastConditions.text = forecast.weather?.description ?: ""
        binding.adapterForecastTemp.text = forecast.getTempString()
        binding.adapterForecastLow.text = forecast.getLowTempString()
        binding.adapterForecastHigh.text = forecast.getHighTempString()

    }
    companion object {
        fun create(parent: ViewGroup): ForecastViewHolder {
            val binding = AdapterForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ForecastViewHolder(binding)
        }
    }
    private fun getUrl(icon: String, res: Resources): String {
        return java.lang.String.format(
            res.getString(R.string.image_holder), icon
        )
    }
}