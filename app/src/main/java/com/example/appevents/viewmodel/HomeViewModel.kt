package com.example.appevents.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: EventRepository): ViewModel() {

    val eventLiveData = MutableLiveData<ArrayList<Event>>()

    fun getEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            val events = withContext(Dispatchers.Default) {
                repository.getEvent()
            }
            eventLiveData.value = events
        }
    }

    class HomeViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}