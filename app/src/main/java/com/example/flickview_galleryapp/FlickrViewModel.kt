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
    // The UI will collect from this StateFlow to get updates
    val photosStateFlow = _photosStateFlow.asStateFlow()

    fun fetchPhotos() {
        viewModelScope.launch {
            try {
                // Assuming fetchInterestingPhotos is a suspend function that returns a List<PhotoItem>
                val photos = withContext(Dispatchers.IO) {
                    flickrApi.fetchInterestingPhotos()
                }
                _photosStateFlow.value = photos // Update StateFlow with the list of photos
            } catch (e: Exception) {
                // Handle any errors here
            }
        }
    }
}