package com.example.appevents.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appevents.data.repository.EventRepository
import com.example.appevents.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.internal.MainDispatcherFactory
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel fetches Event then it should call the repository`() {
        val viewModel = instantiateViewModel()
        val eventMockedList: ArrayList<Event> = ArrayList()

        eventMockedList.add(Event("1", "Evento 1", 1231321, "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202))
        eventMockedList.add(Event("2", "Evento 2", 1231321, "2.22", "Evento 2 descricao", "evento 2 imagem", -123.32,32.202))
        eventMockedList.add(Event("3", "Evento 3", 1231321, "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202))

        coEvery { repository.getEvent() } returns eventMockedList

        viewModel.getEvent()

        coVerify { repository.getEvent() }
    }

    private fun instantiateViewModel(): HomeViewModel {
        return HomeViewModel(repository)
    }
}