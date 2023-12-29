package com.example.flickview_galleryapp

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

    fun onEvent(event: UserInputEvents){
        when(event){
            is UserInputEvents.SelectedPhotoItem ->{
                _selectedPhoto.value = event.photoItem
            }
        }
    }
}

sealed class UserInputEvents{
    data class SelectedPhotoItem(val photoItem : PhotoItem) : UserInputEvents()
}