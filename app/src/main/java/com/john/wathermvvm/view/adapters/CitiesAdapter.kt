package com.john.wathermvvm.view.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.john.wathermvvm.R
import com.john.wathermvvm.databinding.AdapterCityItemBinding
import com.john.wathermvvm.model.City
import com.john.wathermvvm.xutil.TimeAgoFormatter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitiesAdapter(
    private val listener: WeatherListener
) : ListAdapter<City, RecyclerView.ViewHolder>(WeatherDiffCallback())  {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val city = getItem(position)

        (holder as ViewHolder).bind(city)

        holder.itemView.setOnLongClickListener{
            listener.onLongPressed(city)
            true
        }
        holder.itemView.setOnClickListener {listener.onClick(city)}
    }

    class ViewHolder private constructor(private val binding: AdapterCityItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val format: TimeAgoFormatter = TimeAgoFormatter()

        fun bind(weatherModel: City){
            var formattedDate = format.format(weatherModel.lastUpdate)
            if (formattedDate.contains("moments")){ formattedDate = "1m"}

            val imageUrl = getUrl(weatherModel.weather?.icon.toString(),binding.root.resources)
           Picasso.get()
               .load(imageUrl)
               .into(binding.adapterCityIcon)
           binding.adapterCityName.text = weatherModel.cityName
           binding.adapterCityTemperature.text = weatherModel.getTempString()
           binding.adapterCityTime.text = formattedDate
           binding.adapterCityDescription.text = weatherModel.weather?.description ?: ""


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdapterCityItemBinding.inflate(layoutInflater, parent, false)
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

    class WeatherListener(val clickListener: (cityId: Long) -> Unit,val longPressListener: (cityId: Long) -> Unit) {
        fun onClick(weather: City) = clickListener(weather.id!!)
        fun onLongPressed(weather: City) = longPressListener(weather.id!!)
    }

    fun submitCityList(list: List<City>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }


}