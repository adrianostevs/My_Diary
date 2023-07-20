package com.learn.mydiary.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.remote.model.response.ListStoryResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.ui.maps.MapsViewModel
import com.learn.mydiary.util.getOrAwaitValue
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyListMaps = DummyData.dummyListMap()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when get story from location success and Result Success`() {

        val expectedStory = MutableLiveData<ResultResponse<ListStoryResponse>>()
        expectedStory.value = ResultResponse.Success(dummyListMaps)
        `when`(mapsViewModel.getAllLocation()).thenReturn(expectedStory)

        val actualStory = mapsViewModel.getAllLocation().getOrAwaitValue()

        Mockito.verify(storyRepository).getLocation()
        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is ResultResponse.Success)
        Assert.assertEquals(dummyListMaps, (actualStory as ResultResponse.Success).data)
    }
}