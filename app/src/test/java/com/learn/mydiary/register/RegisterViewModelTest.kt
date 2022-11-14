package com.learn.mydiary.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.learn.mydiary.data.DummyData
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.model.response.BaseResponse
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.ui.auth.register.RegisterViewModel
import com.learn.mydiary.util.getOrAwaitValue
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyResponse = DummyData.dummyBaseResponse()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun `when register success and Result Success`() {
        val request = RegisterRequest(name = "Brokoli", email = "brokoli@mail.com", password = "sayur123")
        val expectedResponse = MutableLiveData<ResultResponse<BaseResponse>>()
        expectedResponse.value = ResultResponse.Success(dummyResponse)
        Mockito.`when`(registerViewModel.register(request)).thenReturn(expectedResponse)

        val actualResponse = registerViewModel.register(request).getOrAwaitValue()

        Mockito.verify(userRepository).register(request)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is ResultResponse.Success)
        junit.framework.Assert.assertEquals(
            dummyResponse,
            (actualResponse as ResultResponse.Success).data
        )

    }
}