package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val repository: ProfileRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _contentList = MutableStateFlow<List<Content>>(listOf())
    val contentList = _contentList.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        getUser()
        getIsLoggedIn()
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.update {
                repository.getUserDetails()
            }
            getContentList()
        }
    }

    fun getContentList() {
        viewModelScope.launch {
            _contentList.update {
                async { repository.fetchContentList(user.value!!) }.await()
            }
        }
    }

    fun getIsLoggedIn() {
        viewModelScope.launch {
            _isLoggedIn.update {
                async { repository.isLoggedIn() }.await()
            }
        }
    }
}