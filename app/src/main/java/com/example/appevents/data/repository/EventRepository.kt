package com.example.appevents.data.repository

import android.os.Build
import com.example.appevents.BuildConfig
import com.example.appevents.data.EventsApi
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class EventRepository {
    private lateinit var baseUrl: String

    private fun makeRequest(): EventsApi {
        baseUrl = if(Build.VERSION.SDK_INT <= 21){
            BuildConfig.Base_URL19
        }else {
            BuildConfig.Base_URL
        }

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsApi::class.java)
    }

    suspend fun getEvent(): Response<ArrayList<Event>> {
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
