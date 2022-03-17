package com.example.appevents.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appevents.R
import com.example.appevents.adapter.EventsAdapter
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.databinding.FragmentHomeBinding
import com.example.appevents.exception.CustomException
import com.example.appevents.model.Event
import com.example.appevents.viewmodeltest.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.show()

        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(EventRepository())).get(HomeViewModel::class.java)
        observerEvent()
        homeViewModel.getEvent()
    }

    private fun observerEvent() {
        homeViewModel.eventLiveData.observe(viewLifecycleOwner) {
            when(it){
                is ArrayList<*> -> setupCard(it)

                is CustomException -> {
                    binding.rviewEventsCard.visibility = View.GONE
                    binding.progressBar2.visibility = View.GONE
                    binding.txtBlankState.visibility = View.VISIBLE
                }

                else -> {
                    binding.rviewEventsCard.visibility = View.GONE
                    binding.progressBar2.visibility = View.GONE
                    binding.txtBlankState.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupCard(events: ArrayList<*>) {
        binding.progressBar2.visibility = View.GONE
        binding.rviewEventsCard.visibility = View.VISIBLE
        binding.rviewEventsCard.layoutManager = LinearLayoutManager(requireContext())
        binding.rviewEventsCard.adapter = EventsAdapter(events as ArrayList<Event>, requireContext())
    }
}