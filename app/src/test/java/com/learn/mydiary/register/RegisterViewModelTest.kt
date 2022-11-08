package com.learn.mydiary.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.base.AppResult
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.ui.auth.register.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var registerViewModel: RegisterViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(userRepository)
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
    fun `when register success and Result Success`() = runTest {
        val request = RegisterRequest(name = "Steven", email = "stevenadriano84@gmail.com", password = "dipay123")
        val expected = AppResult.OnSuccess<Flow<BaseResponse?>>(flow {
            emit(
                BaseResponse(
                    error = false,
                    message = "OK"
                )
            )
        }.flowOn(Dispatchers.IO))
        Mockito.`when`(userRepository.register(request)).thenReturn(expected)
        Assert.assertTrue(userRepository.register(request) is AppResult.OnSuccess)

    }
}