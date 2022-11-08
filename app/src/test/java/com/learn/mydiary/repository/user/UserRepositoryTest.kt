package com.learn.mydiary.repository.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.remote.network.ApiService
import com.learn.mydiary.data.repository.UserRepository
import com.learn.mydiary.util.extension.flowAsData
import com.learn.mydiary.util.extension.onCompleteListener
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
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        apiService = ApiServiceTest()
        userRepository = UserRepository(apiService)
    }

    @Test
    fun `when login success and not null`() = runTest {
        val request = LoginRequest(email = "stevenadriano84@gmail.com", password = "dipay123")
        val actual = userRepository.login(request)

        actual.onCompleteListener(
            onSuccess = {
                val login = it?.flowAsData()

                Assert.assertNotNull(login)
                Assert.assertEquals(
                    login,
                    ApiServiceTest.dummyLogin
                )
            },
            onFailure = {_, _ ->},
            onError = {}
        )
    }

    @Test
    fun `when register success and not null`() = runTest {
        val request = RegisterRequest(name = "Steven", email = "stevenadriano84@gmail.com", password = "dipay123")
        val actual = userRepository.register(request)

        actual.onCompleteListener(
            onSuccess = {
                val register = it?.flowAsData()

                Assert.assertNotNull(register)
                Assert.assertEquals(
                    register,
                    ApiServiceTest.dummyRegister
                )
            },
            onError = {},
            onFailure = {_,_ ->}
        )
    }
}