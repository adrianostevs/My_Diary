package com.learn.mydiary.repository.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ListUpdateCallback
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.repository.ApiServiceTest
import com.learn.mydiary.ui.main.MainAdapter
import com.learn.mydiary.util.MainCoroutineRules
import com.learn.mydiary.util.PagedDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var apiService: ApiService

    @Mock
    private var dummyMultipart = DummyData.dummyMultipart()
    private var dummyDescription = DummyData.dummyDescription()

    @Before
    fun setUp() {
        apiService = ApiServiceTest()
    }

    @Test
    fun `when get story map is success`() = runTest {
        val expectedStory = DummyData.dummyListMap()
        val actualStory = apiService.getStories(1, 30, 1)
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

    @Test
    fun `when set story is success`() = runTest {
        val expectedResponse = DummyData.dummyBaseResponse()
        val actualResponse = apiService.setStories(
            dummyDescription,
            dummyMultipart
        )
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `when get story is success`() = runTest {
        val mainCoroutineRule = MainCoroutineRules()

        val dummyStories = DummyData.dummyListStory()
        val data = PagedDataSource.snapshot(dummyStories)

        val expectedResult = flowOf(data)
        Mockito.`when`(storyRepository.getAllStories()).thenReturn(expectedResult)

        storyRepository.getAllStories().collect {
            val differ = AsyncPagingDataDiffer(
                MainAdapter.DIFF_CALLBACK,
                listUpdateCallback,
                mainCoroutineRule.dispatcher,
                mainCoroutineRule.dispatcher
            )

            differ.submitData(it)
            Assert.assertNotNull(differ.snapshot())
            Assert.assertEquals(
                DummyData.generateDummyStoriesResponse().listStory.size,
                differ.snapshot().size
            )
        }
    }

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}