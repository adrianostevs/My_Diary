package com.learn.mydiary.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.response.LoginResultResponse
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.ui.auth.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userRepository)
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
    fun `when login success and Result Success`() = runTest {
        val request = LoginRequest(email = "stevenadriano84@gmail.com", password = "dipay123")
        val expected = AppResult.OnSuccess<Flow<LoginResultResponse?>>(flow {
            emit(
                LoginResultResponse(
                    error = false,
                    message = "OK",
                    LoginResultResponse.LoginResult(
                        userId = "user-GnfHKnXLPfnnQ_PL",
                        name = "Steven",
                        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUduZkhLblhMUGZublFfUEwiLCJpYXQiOjE2Njc2NzAyMDZ9.h-wRZ01z3zQEUXdJhrh7hXJhd9wFjmbwGI34zRRdnkQ"
                    )
                )
            )
        }.flowOn(Dispatchers.IO))
        `when`(userRepository.login(request)).thenReturn(expected)
        Assert.assertTrue(userRepository.login(request) is AppResult.OnSuccess)

    }
}
