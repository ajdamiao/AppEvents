package com.example.appevents.viewmodeltest

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
    val eventLiveData = MutableLiveData<Any>()
    val eventDetailsLiveData = MutableLiveData<Any>()
    val checkInLiveData = MutableLiveData<Any>()

    fun getEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            val events = withContext(Dispatchers.Default) {
                repository.getEvent()
            }
                if(events.isSuccessful) {
                    eventLiveData.value = events.body()
                }
                else {
                    eventLiveData.value = CustomException::class.java
                }
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
            val checkInResponse = withContext(Dispatchers.Default) {
                repository.checkInEvent(checkIn)
            }
            try {

                println("${checkInResponse.isSuccessful}  |  ${checkInResponse.message()} | ${checkInResponse.body()} | ${checkInResponse.code()}")

                if(checkInResponse.code() != 200 || checkInResponse.code() != 204) {
                    checkInLiveData.value = checkInResponse.isSuccessful
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