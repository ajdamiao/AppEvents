package com.example.appevents.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appevents.R
import com.example.appevents.databinding.RviewEventCardListBinding
import com.example.appevents.model.Event
import com.example.appevents.util.Util
import com.example.appevents.view.HomeFragmentDirections
import java.util.*
import kotlin.collections.ArrayList

class EventsAdapter(private val events: ArrayList<Event>, private val context: Context) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    private val util = Util()
    inner class EventsViewHolder(val binding: RviewEventCardListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        val binding = RviewEventCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {
        with(holder) {
            with(events[position]) {

                holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.rview_animation)

                val c = Calendar.getInstance()
                c.timeInMillis = date

                val date = util.dateFormatter(date)
                val eventAddress = util.locationGetter(latitude, longitude, context)

                binding.txtEventLocation.text = eventAddress
                binding.txtEventName.text = title
                binding.txtEventDate.text = date
                binding.txtEventDescription.text = description

                holder.itemView.setOnClickListener {

                    val directions = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(id)
                    Navigation.findNavController(itemView).navigate(directions)
                }

                holder.binding.btnShare.setOnClickListener {
                    util.shareEvent(eventAddress,price,context)
                }

                holder.binding.btnCheckIn.setOnClickListener {
                    val directions = HomeFragmentDirections.actionHomeFragmentToCheckInFragment(title,price,id)
                    Navigation.findNavController(itemView).navigate(directions)
                }
            }
        }
    }

    override fun getItemCount() = events.size
}