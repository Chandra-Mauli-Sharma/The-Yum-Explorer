package com.example.theyumexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.ExplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(val repository: ExplorerRepository) : ViewModel() {
    private val _contentList = MutableStateFlow<List<Content>>(listOf())
    val contentList = _contentList.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        GetUser()
        GetContentList()
    }

    fun GetContentList() {
        viewModelScope.launch {
            _contentList.update {
                async { repository.GetContent() }.await()
            }
        }
    }

    fun GetUser() {
        viewModelScope.launch {
            _user.update {
                async { repository.GetUser() }.await()
            }
        }
    }
}