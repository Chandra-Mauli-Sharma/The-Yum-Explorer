package com.example.theyumexplorer.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.repository.UploadContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadContentViewModel @Inject constructor(
    private val repository: UploadContentRepository,
) :
    ViewModel() {

    private val _content: MutableStateFlow<Content?> = MutableStateFlow(null)
    val content: StateFlow<Content?> = _content

    fun onContentChanged(content: Content) {
        _content.value = content
    }


    fun uploadImage(currentImage: Uri) {
        viewModelScope.launch {
            async { repository.UploadContent(content.value!!, currentImage) }.await()
        }
    }
}