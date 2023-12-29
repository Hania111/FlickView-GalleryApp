package com.example.flickview_galleryapp.network

import android.util.Log
import com.example.flickview_galleryapp.PhotoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FlickrApi {
    private val baseUrl = "https://api.flickr.com/services/rest"
    private val apiKey = ""
    private val tag = "WebServicesFunTag"

    // PODZIELIĆ NA MNIEJSZE FUNKCJE
    suspend fun fetchInterestingPhotos(): List<PhotoItem> = withContext(Dispatchers.IO) {
        val url = constructInterestingPhotoListURL()
        val interestingPhotoList = mutableListOf<PhotoItem>()
        try {
            // Opening URL connection
            val urlObject = URL(url)
            val urlConnection = urlObject.openConnection() as HttpURLConnection

            // Downloading JSON response
            val jsonResult = urlConnection.inputStream.bufferedReader().use { it.readText() }
            Log.d(tag, "fetchInterestingPhotos: $jsonResult")

            // Parse the json
            val jsonObject = JSONObject(jsonResult)
            val photosObject = jsonObject.getJSONObject("photos")
            val photoArray = photosObject.getJSONArray("photo")
            for (i in 0 until photoArray.length()) {
                val singlePhotoObject = photoArray.getJSONObject(i)
                val photoItem = parseInterestingPhoto(singlePhotoObject)
                photoItem?.let {
                    interestingPhotoList.add(it)
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Error fetching photos", e)
        }
        interestingPhotoList
    }

    private fun constructInterestingPhotoListURL(): String {
        return "$baseUrl?method=flickr.interestingness.getList&api_key=$apiKey&format=json&nojsoncallback=1&extras=date_taken,url_h"
    }

    private fun parseInterestingPhoto(jsonObject: JSONObject): PhotoItem? {
        return try {
            val id = jsonObject.getString("id")
            val title = jsonObject.getString("title")
            val dateTaken = jsonObject.getString("datetaken")
            val photoURL = jsonObject.getString("url_h")
            PhotoItem(id, title, dateTaken, photoURL)
        } catch (e: JSONException) {
            // Skipping a photo in case of JSON parsing error
            null
        }
    }
}
