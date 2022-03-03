package com.example.appevents.data.repository

import com.example.appevents.data.EventsApi
import com.example.appevents.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {
    private val baseURL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

    private fun makeRequest(): EventsApi {
        return Retrofit
            .Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsApi::class.java)
    }

    suspend fun getEvent(): ArrayList<Event> {
        return withContext(Dispatchers.IO) {
            makeRequest().getEvents()
        }
    }
}
