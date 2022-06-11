package com.john.wathermvvm.view.adapters.city

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.john.wathermvvm.model.City
import com.john.wathermvvm.util.margins

class CityAdapter(private val listener: WeatherListener) :
    ListAdapter<City, ViewHolder>(MODEL_COMPARATOR) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return CityViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val city = getItem(position)

    (holder as CityViewHolder).bind(city)

    holder.itemView.setOnLongClickListener {
      listener.onLongPressed(city)
      true
    }
    val margin = 16
    if (position == 0) {
      holder.itemView.margins(0, 0, 0, 0)
    }
    if (position == currentList.size - 1) {
      holder.itemView.margins(0, 0, 0, margin)
    }
    holder.itemView.setOnClickListener { listener.onClick(city) }
  }

  class WeatherListener(
      val clickListener: (cityId: Long) -> Unit,
      val longPressListener: (cityId: Long) -> Unit
  ) {
    fun onClick(city: City) = clickListener(city.id!!)
    fun onLongPressed(city: City) = longPressListener(city.id!!)
  }

  companion object {
    private val MODEL_COMPARATOR =
        object : DiffUtil.ItemCallback<City>() {
          override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return (oldItem == newItem)
          }

          override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
              oldItem == newItem
        }
  }
}
