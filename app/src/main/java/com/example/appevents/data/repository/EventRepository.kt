package com.example.appevents.data.repository

import com.example.appevents.BuildConfig
import com.example.appevents.data.EventsApi
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {

    private fun makeRequest(): EventsApi {
        return Retrofit
            .Builder()
            .baseUrl((BuildConfig.Base_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsApi::class.java)
    }

    suspend fun getEvent(): ArrayList<Event> {
        return withContext(Dispatchers.IO) {
            makeRequest().getEvents()
        }
    }

    suspend fun getEventDetails(id: String): Event {
        return withContext(Dispatchers.IO) {
            makeRequest().getEventDetail(id)
        }
    }

    suspend fun checkInEvent(checkIn: CheckIn): Response<*> {
        return withContext(Dispatchers.IO) {
            makeRequest().checkInEvent(checkIn)
        }
    }
}
