package com.example.androidbootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbootcamp.common.Resource
import com.example.androidbootcamp.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<Resource<AuthResult>>(Resource.Loading())
    val loginResult: StateFlow<Resource<AuthResult>> = _loginResult

    private val _registrationResult = MutableStateFlow<Resource<AuthResult>>(Resource.Loading())
    val registrationResult: StateFlow<Resource<AuthResult>> = _registrationResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.loginUser(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.registerUser(email, password).collect {
                _registrationResult.value = it
            }
        }
    }
}
