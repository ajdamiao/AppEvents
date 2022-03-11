package com.example.appevents.viewmodeltest

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appevents.data.EventsApi
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest : TestCase() {

    private val testDispatcher = TestCoroutineDispatcher()
    private val apiService: EventsApi = mockk()
    private val repository = mockk<EventRepository>()
    private val eventObserver: Observer<ArrayList<Event>> = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when view model fetches event data the it should call the repository to fetch Events`() {

        val eventList = ArrayList<Event>()
        eventList.add(Event("3", "Evento 3", 1231321, "33", "Descricao evento 3","Imagem evento 3", 33.3312,33.3213))
        eventList.add(Event("1", "Evento 1", 1231321, "31", "Descricao evento 1","Imagem evento 1", 11.3312,11.3213))
        eventList.add(Event("2", "Evento 2", 1231321, "32", "Descricao evento 2","Imagem evento 2", 22.3312,22.3213))


        coEvery { repository.getEvent() } returns eventList
        instantiateViewModel()

        coVerify { repository.getEvent() }
    }

    private fun instantiateViewModel(): HomeViewModel {
        val viewModel = HomeViewModel(repository)
        viewModel.eventLiveData.observeForever(eventObserver)
        return viewModel
    }
}