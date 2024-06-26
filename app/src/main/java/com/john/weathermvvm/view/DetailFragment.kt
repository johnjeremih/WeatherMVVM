package com.john.weathermvvm.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.john.weathermvvm.R
import com.john.weathermvvm.databinding.DetailFragmentBinding
import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.repository.network.NetworkDataState
import com.john.weathermvvm.view.adapters.forecast.ForecastAdapter
import com.john.weathermvvm.viewmodel.DetailViewModel
import com.john.weathermvvm.util.TimeAgoFormatter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var cityId: Long? = null
    private lateinit var binding: DetailFragmentBinding
    private val viewModel: DetailViewModel by viewModels()
    private val adapterScope = CoroutineScope(Dispatchers.IO)

    private val format: TimeAgoFormatter = TimeAgoFormatter()
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityId = arguments?.getLong("cityId")
        viewModel.getCity(cityId, false)

        binding.detailToolbar.inflateMenu(R.menu.refresh)

        binding.detailToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_refresh -> {
                    viewModel.getCity(cityId, true)
                }

            }
            true
        }

        binding.detailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.detailToolbar.setNavigationOnClickListener {
            Navigation.findNavController(
                binding.root
            ).navigateUp()
        }



        setObservers()
    }

    private fun setObservers() {

        viewModel.cityNetworkState.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkDataState.Success<City> -> {
                    isProgressBarVisible(false)
                    setContent(it.data)
                }
                is NetworkDataState.Error -> {
                    isProgressBarVisible(false)
                    Snackbar.make(
                        requireView(),
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is NetworkDataState.Loading -> {
                    isProgressBarVisible(true)
                }
            }
        }

        viewModel.forecastNetworkState.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkDataState.Success<List<Forecast>> -> {
                    isProgressBarVisible(false)
                    setForecast(it.data)
                }
                is NetworkDataState.Error -> {
                    isProgressBarVisible(false)
                    Snackbar.make(
                        requireView(),
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is NetworkDataState.Loading -> {
                    isProgressBarVisible(true)
                }
            }
        }

    }

    private fun setForecast(data: List<Forecast>) {

        adapter = ForecastAdapter()
        val linearLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.detailRecyclerView.layoutManager = linearLayoutManager
        binding.detailRecyclerView.adapter = adapter

        adapterScope.launch {
            adapter.submitList(data)
        }

    }


    private fun isProgressBarVisible(b: Boolean) {
        binding.detailProgressbar.visibility = if (b) View.VISIBLE else View.GONE
    }


    private fun setContent(weatherModel: City) {

        var formattedDate = format.format(weatherModel.lastUpdate)
        if (formattedDate.contains("moments")) {
            formattedDate = "1m"
        }


        val imageUrl = getUrl(weatherModel.weather?.icon.toString(), binding.root.resources)
        Picasso.get()
            .load(imageUrl)
            .into(binding.detailCityIcon)


        binding.detailCityName.text = weatherModel.cityName
        binding.detailCityTemperature.text = weatherModel.getTempString()
        binding.detailCityTime.text = formattedDate
        binding.detailCityDescription.text = weatherModel.weather?.description ?: ""
        binding.detailToolbar.title = weatherModel.cityName
    }

    private fun getUrl(icon: String, res: Resources): String {
        return java.lang.String.format(
            res.getString(R.string.image_holder), icon
        )
    }

}