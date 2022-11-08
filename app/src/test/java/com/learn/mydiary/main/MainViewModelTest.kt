package com.learn.mydiary.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.StoryDataSource
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var storyDataSource: StoryDataSource

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(storyRepository)
    }

    @Before
    fun setUpDispatcher(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when get story success and Result Success`() = runTest {
        val request = StoryRequest(page = 1)
        val expected = AppResult.OnSuccess(Pager(PagingConfig(pageSize = 15)){
            storyDataSource.apply { setRequest(request) }
        }.flow)
        Mockito.`when`(storyRepository.getAllStory(request)).thenReturn(expected)
        Assert.assertTrue(storyRepository.getAllStory(request) is AppResult.OnSuccess)

    }
}