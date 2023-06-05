package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    fun createAccount() {
        viewModelScope.launch {
            repository.CreateAccount()
        }
    }

    fun createAnonymousSession() {
        viewModelScope.launch {
            async { repository.LoginAnonymous() }.await()

        }
    }
}