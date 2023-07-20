package com.learn.mydiary.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.ui.addstory.AddStoryViewModel
import com.learn.mydiary.util.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mStoryRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(mStoryRepository)
    }

    @Test
    fun `when set story success and Result Success`() {
        val file = mock(File::class.java)
        val description = "Description".toRequestBody("text/plain".toMediaType())
        val imageMultipart = MultipartBody.Part.createFormData(
            "image", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
        )

        val expectedResponse = MutableLiveData<ResultResponse<BaseResponse>>()
        expectedResponse.value = ResultResponse.Success(BaseResponse(error = false, message = "OK"))
        `when`(
            addStoryViewModel.setStory(
                description,
                imageMultipart
            )
        ).thenReturn(
            expectedResponse
        )

        val actualResponse =
            addStoryViewModel.setStory(description, imageMultipart)
                .getOrAwaitValue()

        Mockito.verify(mStoryRepository)
            .setStory(description, imageMultipart)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is ResultResponse.Success)
        assertEquals(BaseResponse(error = false, message = "OK"), (actualResponse as ResultResponse.Success).data)
    }
}