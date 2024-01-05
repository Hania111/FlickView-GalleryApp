package com.example.flickview_galleryapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickview_galleryapp.network.FlickrApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlickrViewModel : ViewModel(){
    private val flickrApi = FlickrApi()
    private val _photosStateFlow = MutableStateFlow<List<PhotoItem>>(emptyList())
    val photosStateFlow = _photosStateFlow.asStateFlow()

    private val _authorInfo = MutableStateFlow<PhotoAuthor?>(null)
    val authorInfo = _authorInfo.asStateFlow()

    private val _selectedPhoto = MutableStateFlow<PhotoItem?>(null)
    val selectedPhoto = _selectedPhoto.asStateFlow()

    fun fetchPhotos() {
        viewModelScope.launch {
            try {
                val photos = withContext(Dispatchers.IO) {
                    flickrApi.fetchInterestingPhotos()
                }
                _photosStateFlow.value = photos
            } catch (e: Exception) {

            }
        }
    }

    fun fetchAuthorInfo(authorId : String) {
        viewModelScope.launch {
            try {
                val info = withContext(Dispatchers.IO) {
                    flickrApi.fetchAuthorInformation(authorId)
                }
                _authorInfo.value = info
            } catch (e: Exception) {

            }
        }
    }

    fun onEvent(event: UserInputEvents){
        when(event){
            is UserInputEvents.SelectedPhotoItem ->{
                _selectedPhoto.value = event.photoItem
                fetchAuthorInfo(event.photoItem.owner)
            }
        }
    }
}

sealed class UserInputEvents{
    data class SelectedPhotoItem(val photoItem : PhotoItem) : UserInputEvents()
}