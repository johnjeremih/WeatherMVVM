package com.john.weathermvvm.view.adapters.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.util.margins

class ForecastAdapter : ListAdapter<Forecast, RecyclerView.ViewHolder>(MODEL_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ForecastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecast = getItem(position)

        (holder as ForecastViewHolder).bind(forecast)

        if (position==0){
            holder.itemView.margins(16,0,0,0)
        }
        if (position==currentList.size-1){
            holder.itemView.margins(0,16,0,0)
        }

    }


    companion object {
        private val MODEL_COMPARATOR = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
                oldItem == newItem
        }
    }



}