package com.learn.mydiary.ui.auth.register

import androidx.lifecycle.ViewModel
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun register(registerRequest: RegisterRequest) = userRepository.register(registerRequest)

}