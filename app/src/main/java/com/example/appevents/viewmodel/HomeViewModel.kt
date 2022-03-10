package com.example.appevents.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.exception.CustomException
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class HomeViewModel(private val repository: EventRepository): ViewModel() {
    val eventLiveData = MutableLiveData<ArrayList<Event>>()
    val eventDetailsLiveData = MutableLiveData<Event>()
    val checkInLiveData = MutableLiveData<Any>()

    fun getEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            val events = withContext(Dispatchers.Default) {
                repository.getEvent()
            }
            eventLiveData.value = events
        }
    }

    fun getEventDetails(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val eventDetail = withContext(Dispatchers.Default) {
                repository.getEventDetails(id)
            }
            eventDetailsLiveData.value = eventDetail
        }
    }

    fun doCheckIn(checkIn: CheckIn) {
        CoroutineScope(Dispatchers.Main).launch {
            val checkIn = withContext(Dispatchers.Default) {
                repository.checkInEvent(checkIn)
            }
            try {
                if(checkIn.code() != 200 || checkIn.code() != 204) {
                    checkInLiveData.value = checkIn.isSuccessful
                }
            }catch (exception: Exception) {
                when(exception) {
                    is HttpException -> {
                        val jsonParsed = JSONObject(exception.response()?.errorBody().toString())
                        val gson = Gson()
                        val cException = gson.fromJson(jsonParsed.toString(), CustomException::class.java)

                        checkInLiveData.value = cException
                    }
                }
            }
        }
    }

    class HomeViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}