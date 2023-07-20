package com.learn.mydiary.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.ui.auth.login.LoginViewModel
import com.learn.mydiary.util.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyResponse = DummyData.dummyLoginResponse()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userRepository)
    }

    @Test
    fun `when login() is success and ResultResponse is Success`() {
        val request = LoginRequest(email = "brokoli@mail.com", password = "sayur123")
        val expectedResponse = MutableLiveData<ResultResponse<LoginResultResponse>>()
        expectedResponse.value = ResultResponse.Success(dummyResponse)
        `when`(loginViewModel.login(request)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(request).getOrAwaitValue()

        Mockito.verify(userRepository).login(request)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is ResultResponse.Success)
        assertEquals(dummyResponse, (actualResponse as ResultResponse.Success).data)
    }
}
