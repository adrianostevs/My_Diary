package com.learn.mydiary.repository.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.repository.ApiServiceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = ApiServiceTest()
    }

    @Test
    fun `when register is success`() = runTest {
        val expectedResponse = DummyData.dummyBaseResponse()
        val actualResponse =
            apiService.register(RegisterRequest("brokoli", "brokoli@mail.com", "cpktnwt456"))
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when login is success`() = runTest {
        val expectedResponse = DummyData.dummyLoginResponse()
        val actualResponse = apiService.login(LoginRequest("brokoli@gmail.com", "cpktnwt456"))
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }
}