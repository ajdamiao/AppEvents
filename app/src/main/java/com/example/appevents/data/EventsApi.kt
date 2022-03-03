package com.example.appevents.data

import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    @GET("events/")
    suspend fun getEvents(): ArrayList<Event>

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") id: String): Event

    @POST("checkin")
    suspend fun checkInEvent(@Body checkIn: CheckIn): Response<*>

}