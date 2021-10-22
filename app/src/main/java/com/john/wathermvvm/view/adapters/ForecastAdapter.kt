package com.john.wathermvvm.view.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.john.wathermvvm.R
import com.john.wathermvvm.databinding.AdapterForecastItemBinding
import com.john.wathermvvm.model.City
import com.john.wathermvvm.xutil.TimeFormatter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastAdapter : ListAdapter<City, RecyclerView.ViewHolder>(WeatherDiffCallback())  {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val city = getItem(position)

        (holder as ViewHolder).bind(city)

    }

    class ViewHolder private constructor(private val binding: AdapterForecastItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val format: TimeFormatter = TimeFormatter()

        fun bind(weatherModel: City){
            val formattedTime = format.format(weatherModel.dateTime)

         val imageUrl = getUrl(weatherModel.weather?.icon.toString(),binding.root.resources)
           Picasso.get()
               .load(imageUrl)
               .into(binding.adapterForecastIcon)

            binding.adapterForecastTime.text = formattedTime
            binding.adapterForecastConditions.text = weatherModel.weather?.description ?: ""
            binding.adapterForecastTemp.text = weatherModel.getTempString()
            binding.adapterForecastLow.text = weatherModel.getLowTempString()
            binding.adapterForecastHigh.text = weatherModel.getHighTempString()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdapterForecastItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        private fun getUrl(icon: String, res: Resources): String {
            return java.lang.String.format(
                res.getString(R.string.image_holder), icon
            )
        }

    }


    class WeatherDiffCallback : DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    fun submitForecastList(list: List<City>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }


}