package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val repository: SignupRepository
) : ViewModel() {

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    fun OnPasswordChanged(password: String) {
        _password.value = password
    }

    private val _repassword = MutableStateFlow<String>("")
    val repassword = _repassword.asStateFlow()

    fun OnRePasswordChanged(password: String) {
        _repassword.value = password
    }

    fun CreateGoogleAccount(idtoken: String) {
        viewModelScope.launch {
            _user.value = async { repository.CreateGoogleAccount(idtoken) }.await()
        }
    }

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun OnUserChanged(user: User) {
        _user.value = user
    }

    fun GetUser() {
        viewModelScope.launch {
            _user.value = async { repository.GetUser() }.await()
        }
    }

    fun CreateAccountWithEmailAndPassword() {
        viewModelScope.launch {
            async {
                repository.CreateUserWithEmailAndPassword(
                    email = user.value?.email!!,
                    password.value,
                    name = user.value?.name!!
                )
            }.await()
        }
    }
}