package com.learn.mydiary.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.ui.auth.login.LoginViewModel
import com.learn.mydiary.ui.main.MainAdapter
import com.learn.mydiary.ui.main.MainViewModel
import com.learn.mydiary.util.MainCoroutineRules
import com.learn.mydiary.util.PagedDataSource
import com.learn.mydiary.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRules = MainCoroutineRules()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mainViewModel: MainViewModel
    private val dummyStory = DummyData.dummyListStory()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(storyRepository)
    }

    @Test
    fun `when Get Story is Success and Result Success`() = runTest {
        val data = PagingData.from(dummyStory)
        Mockito.`when`(storyRepository.getAllStories()).thenReturn(flowOf(data))

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRules.dispatcher,
            workerDispatcher = mainCoroutineRules.dispatcher,
        )

        val actualStory = mainViewModel.getStory().getOrAwaitValue()

        differ.submitData(actualStory)

        advanceUntilIdle()
        Mockito.verify(storyRepository).getAllStories()
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {

        // Given
        val data: PagingData<Story> = PagingData.empty()
        Mockito.`when`(storyRepository.getAllStories()).thenReturn(flowOf(data))

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRules.dispatcher,
            workerDispatcher = mainCoroutineRules.dispatcher,
        )

        // When
        val actualStory = mainViewModel.getStory().getOrAwaitValue()
        differ.submitData(actualStory)

        // Then
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}