package com.john.wathermvvm.view

import android.app.AlertDialog
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.john.wathermvvm.R
import com.john.wathermvvm.repository.network.NetworkDataState
import com.john.wathermvvm.databinding.HomeFragmentBinding
import com.john.wathermvvm.model.City
import com.john.wathermvvm.view.adapters.CitiesAdapter
import com.john.wathermvvm.viewmodel.HomeViewModel
import com.john.wathermvvm.xutil.AddressAutoComplete
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: CitiesAdapter
    private var launcher = registerForActivityResult(AddressAutoComplete()) { place ->

        if (place != null) {
            val geocoder = Geocoder(activity)
            viewModel.setCity(place.address, geocoder)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentList()
        setObservers()
        binding.searchView.setOnClickListener { launcher.launch() }


    }

    private fun setObservers() {
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkDataState.Success<List<City>> -> {
                    isProgressBarVisible(false)
                    setContent(it.data)
                }
                is NetworkDataState.Error -> {
                    isProgressBarVisible(false)
                    binding.emptyText.text = it.exception.message
                    binding.emptyText.visibility = View.VISIBLE
                }
                is NetworkDataState.Loading -> {
                    isProgressBarVisible(true)
                }
            }


        }

        viewModel.deleteState.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkDataState.Success<String> -> {
                    isProgressBarVisible(false)
                    Toast.makeText(context, "The city was deleted successfully", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.getCurrentList()

                }
                is NetworkDataState.Error -> {
                    isProgressBarVisible(false)
                    binding.emptyText.text = it.exception.message
                    binding.emptyText.visibility = View.VISIBLE
                }
                is NetworkDataState.Loading -> {
                    isProgressBarVisible(true)
                }
            }


        }
    }

    private fun isProgressBarVisible(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE

    }


    private fun setContent(weatherList: List<City>) {
        initRecyclerView()
        adapter.submitCityList(weatherList)

    }

    private fun initRecyclerView() {

        adapter = CitiesAdapter(CitiesAdapter.WeatherListener({
            val bundleShop = Bundle()
            bundleShop.putLong("cityId", it)
            Navigation.findNavController(binding.root).navigate(R.id.toDetailFragment, bundleShop)
        }, {
            showDialog(it)

        }))
        val linearLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun showDialog(it: Long) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.apply {
            setTitle("Are you sure you want to delete this city?")
            setPositiveButton("Yes") { _, _ ->
                run {
                    viewModel.deleteUser(it)
                }
            }
            setNegativeButton("No") { _, _ ->
            }

            setCancelable(false)
        }.create().show()


    }

}