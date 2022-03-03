package com.example.appevents.adapter

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appevents.databinding.RviewEventCardListBinding
import com.example.appevents.model.Event
import java.util.*
import kotlin.collections.ArrayList

class EventsAdapter(private val events: ArrayList<Event>, private val context: Context) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    inner class EventsViewHolder(val binding: RviewEventCardListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        val binding = RviewEventCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {
        with(holder) {
            with(events[position]) {
                val c = Calendar.getInstance()
                c.timeInMillis = date

                val date = "${c[Calendar.DAY_OF_MONTH]}/${c[Calendar.MONTH]}/${c[Calendar.YEAR]}"
                val geocoder = Geocoder(context)
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                val eventAddress = addresses[0].getAddressLine(0)


                binding.txtEventLocation.text = eventAddress
                binding.txtEventName.text = title
                binding.txtEventDate.text = date
                binding.txtEventDescription.text = description

                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    //Navigation.findNavController(itemView).navigate(R.id.eventDetailsFragment, bundle)
                }

                holder.binding.btnShare.setOnClickListener {
                    shareEvent(eventAddress, price)
                }
            }
        }
    }

    private fun shareEvent(eventAddress: String, eventPrice: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Veja só esse evento em $eventAddress por R$$eventPrice")
        sharingIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sharingIntent, "Compartilhar Evento"))
    }

    override fun getItemCount() = events.size
}