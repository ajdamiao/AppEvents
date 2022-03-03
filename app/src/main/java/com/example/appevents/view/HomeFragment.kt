package com.example.appevents.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appevents.R
import com.example.appevents.adapter.EventsAdapter
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.databinding.FragmentHomeBinding
import com.example.appevents.model.Event
import com.example.appevents.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(EventRepository())).get(HomeViewModel::class.java)
        observerEvent()
        homeViewModel.getEvent()
    }

    private fun observerEvent() {
        homeViewModel.eventLiveData.observe(viewLifecycleOwner) {
            when(it){
                is ArrayList<Event> -> setupCard(it)

                else ->  println("ERROR, $it")
            }
        }
    }

    private fun setupCard(events: ArrayList<Event>) {
        binding.rviewEventsCard.layoutManager = LinearLayoutManager(requireContext())
        binding.rviewEventsCard.adapter = EventsAdapter(events, requireContext())
    }
}