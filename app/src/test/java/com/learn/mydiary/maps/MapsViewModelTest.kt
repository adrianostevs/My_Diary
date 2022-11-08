package com.learn.mydiary.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.ListStory
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.ui.maps.MapsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel

    val testDispatcher : TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get story from location success and Result Success`() = runTest {
        val request = StoryRequest(location = 1)
        val expected = AppResult.OnSuccess<Flow<ListStoryResponse?>>(flow {
            emit(
                ListStoryResponse(
                    error = false,
                    message = "OK",
                    listStory = arrayListOf(
                        ListStory(
                            id = "story-t_3Hcy48HaWACzSz",
                            lat = 200.0,
                            lon = 192.8,
                            description = "blablabla",
                            name = "Steven",
                            createdAt = "2022-11-05T15:36:16.698Z",
                            photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667662576694_l0lB-JvS.jpg"
                        ),
                        ListStory(
                            id = "story-t_3Hcy48HaWACzSz",
                            lat = 200.0,
                            lon = 192.8,
                            description = "blablabla",
                            name = "Steven",
                            createdAt = "2022-11-05T15:36:16.698Z",
                            photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667662576694_l0lB-JvS.jpg"
                        )
                    )
                )
            )
        }.flowOn(Dispatchers.IO))
        `when`(storyRepository.getLocation(request)).thenReturn(expected)
        Assert.assertTrue(storyRepository.getLocation(request) is AppResult.OnSuccess)
    }
}