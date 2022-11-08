package com.learn.mydiary.repository.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.data.repository.StoryRepository
import com.learn.mydiary.repository.user.ApiServiceTest
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        apiService = ApiServiceTest()
        storyRepository = StoryRepository(apiService)
    }

    @Test
    fun `when list stories success and Response Success`() = runTest {
        val request = StoryRequest(page = 1)
        val actual = storyRepository.getAllStoryTest(request)

        actual.onCompleteListener(
            onSuccess = {
                val listStory = it?.flowAsData()

                Assert.assertNotNull(listStory)
                Assert.assertEquals(
                    listStory, ApiServiceTest.dummyStory
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }

    @Test
    fun `when get map stories success and Response Success`() = runTest {
        val request = StoryRequest(location = 1)
        val actual = storyRepository.getLocation(request)

        actual.onCompleteListener(
            onSuccess = {
                val listStory = it?.flowAsData()

                Assert.assertNotNull(listStory)
                Assert.assertEquals(
                    listStory, ApiServiceTest.dummyStory
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }

    @Test
    fun `when set stories success and Response Success`() = runTest {
        val file = Mockito.mock(File::class.java)

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
        val actual = storyRepository.setStory(request)

        actual.onCompleteListener(
            onSuccess = {
                val listStory = it?.flowAsData()
                Assert.assertNotNull(listStory)
                Assert.assertEquals(
                    listStory, ApiServiceTest.dummySetStory
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }
}