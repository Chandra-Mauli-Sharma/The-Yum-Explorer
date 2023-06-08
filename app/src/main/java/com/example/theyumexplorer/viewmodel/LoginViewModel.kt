package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    fun OnUserChanged(user: User) {
        _user.value = user
    }

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()
    fun OnPasswordChanged(password: String) {
        _password.value = password
    }

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()
    fun OnEmailChanged(email: String) {
        _email.value = email
    }

    fun SignInWithEmailAndPassword() {
        viewModelScope.launch {
            _user.value = async {
                repository.SignInWithEmailAndPassword(
                    email.value,
                    password.value
                )
            }.await()
        }
    }

    fun createAnonymousSession() {
        viewModelScope.launch {
            async { repository.LoginAnonymous() }.await()
        }
    }

    fun CreateGoogleAccount(idtoken: String) {
        viewModelScope.launch {
            _user.value = async { repository.SigninGoogleAccount(idtoken) }.await()
        }
    }
}