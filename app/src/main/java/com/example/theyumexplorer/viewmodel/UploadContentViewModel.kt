package com.example.theyumexplorer.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.repository.LoginRepository
import com.example.theyumexplorer.repository.UploadContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadContentViewModel @Inject constructor(private val repository: UploadContentRepository) :
    ViewModel() {
    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> = _title

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    private val _description: MutableStateFlow<String> = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    private val _content: MutableStateFlow<File?> = MutableStateFlow(null)
    val content: StateFlow<File?> = _content

    fun onContentChanged(content: File) {
        _content.value = content
    }

    suspend fun uploadImage(){
        viewModelScope.launch {
            repository.UploadImage(content.value!!)
        }
    }
}