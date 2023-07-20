package com.learn.mydiary.ui.auth.login

import androidx.lifecycle.ViewModel
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

   fun login(loginRequest: LoginRequest) = userRepository.login(loginRequest)
}