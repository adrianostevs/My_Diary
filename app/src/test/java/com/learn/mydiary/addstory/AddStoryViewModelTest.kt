package com.learn.mydiary.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.ui.addstory.AddStoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mStoryRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(mStoryRepository)
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
    fun `when set story success and Result Success`() = runTest {
        val file = mock(File::class.java)

        val part = MultipartBody.Builder()
        part.setType(MultipartBody.FORM)

        if (file != null)
            part.addFormDataPart(
                "photo",
                "addStoryRequest.jpeg",
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        part.addFormDataPart(
            "description",
            "broh"
        )

        val request = part.build()
        val expected = AppResult.OnSuccess<Flow<BaseResponse?>>(flow {
            emit(
                BaseResponse(
                    error = false,
                    message = "OK"
                )
            )
        }.flowOn(Dispatchers.IO))
        `when`(mStoryRepository.setStory(request)).thenReturn(expected)
        Assert.assertTrue(mStoryRepository.setStory(request) is AppResult.OnSuccess)

    }
}