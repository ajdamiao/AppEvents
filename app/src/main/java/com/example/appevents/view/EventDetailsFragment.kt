package com.example.appevents.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appevents.MainActivity
import com.example.appevents.R
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.databinding.FragmentEventDetailsBinding
import com.example.appevents.model.Event
import com.example.appevents.util.Util
import com.example.appevents.viewmodeltest.HomeViewModel

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private lateinit var binding: FragmentEventDetailsBinding
    private lateinit var homeViewModel: HomeViewModel
    private var eventAddress = String()
    private var eventName = String()
    private var eventPrice = String()
    private var eventID = String()
    private var eventImage = String()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventDetailsBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarTitle(getString(R.string.text_toolbar_event_list))

        val id = requireArguments().getString("id")
        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(EventRepository())).get(HomeViewModel::class.java)

        if (id != null) {
            observerEventDetails()
            homeViewModel.getEventDetails(id)
        }

        binding.btnShowEventDescription.setOnClickListener {
            showMoreOrLessDescription()
        }

        binding.btnShare.setOnClickListener {
            util.shareEvent(eventAddress,eventPrice,requireContext())
        }

        binding.btnCheckIn.setOnClickListener {
            val directions = EventDetailsFragmentDirections.actionEventDetailsFragmentToCheckInFragment(eventName,eventPrice,eventID, eventImage)
            findNavController().navigate(directions)
        }
    }

    private fun observerEventDetails() {
        homeViewModel.eventDetailsLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Event -> {
                    setupEventInfo(it)
                    showContent()
                }

                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.eventContent.visibility = View.GONE
                    binding.containerActions.visibility = View.GONE
                }
            }
        }
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        binding.eventContent.visibility = View.VISIBLE
        binding.containerActions.visibility = View.VISIBLE
    }

    private fun setupEventInfo(response: Event) {
        eventAddress = util.getEventLocation(response.latitude, response.longitude, requireContext())
        eventPrice = response.price
        eventName = response.title
        eventID = response.id
        eventImage = response.image

        binding.txtEventTitle.text = eventName
        binding.txtEventDescription.text = response.description
        binding.txtEventPrice.text = String.format("%s %s", getString(R.string.txt_event_price), eventPrice)
        binding.txtEventDate.text = util.dateFormatter(response.date)
        binding.txtEventLocation.text = eventAddress

        Glide
            .with(requireView())
            .load(eventImage)
            .placeholder(R.drawable.ic_image_not_found)
            .into(binding.imgEvent)
    }

    private fun showMoreOrLessDescription() {
        val btnShowMore = binding.btnShowEventDescription

        btnShowMore.setOnClickListener {
            if (btnShowMore.text.equals(getString(R.string.more_event_details))) {
                binding.txtEventDescription.maxLines = Int.MAX_VALUE
                btnShowMore.text = getString(R.string.less_event_details)
            } else {
                binding.txtEventDescription.maxLines = 3
                btnShowMore.text = getString(R.string.more_event_details)
            }
        }
    }
}