package com.example.flickview_galleryapp.network

import android.nfc.Tag
import android.os.AsyncTask
import android.util.Log
import com.example.flickview_galleryapp.MainActivity
import com.example.flickview_galleryapp.PhotoItem
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FlickrApi(){
    private val baseUrl = "https://api.flickr.com/services/rest"
    private val apiKey = "" // Hide this key before pushing to a public repository
    private val tag = "WebServicesFunTag"

    fun fetchInterestingPhotos() : List<PhotoItem>{
        val url = constructInterestingPhotoListURL()
        //Log.d(tag, "fetchInterestingPhotos: $url")

        val asyncTask = FetchInterestingPhotoListAsyncTask()
        asyncTask.execute(url)
    }

    private fun constructInterestingPhotoListURL(): String {
        return "$baseUrl?method=flickr.interestingness.getList&api_key=$apiKey&format=json&nojsoncallback=1&extras=date_taken,url_h"
    }

    class FetchInterestingPhotoListAsyncTask :
        AsyncTask<String, Void, List<PhotoItem>>() {

        override fun doInBackground(vararg strings: String): List<PhotoItem> {
            val url = strings[0]
            val interestingPhotoList = mutableListOf<PhotoItem>()

            try {
                // Opening URL connection
                val urlObject = URL(url)
                val urlConnection = urlObject.openConnection() as HttpURLConnection
                // Downloading JSON response
                var jsonResult = ""
                val inStream = urlConnection.inputStream
                val reader = InputStreamReader(inStream)
                var data = reader.read()
                while (data != -1) {
                    jsonResult += data.toChar()
                    data = reader.read()
                }
                Log.d(TAG, "doInBackground: $jsonResult")

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
                e.printStackTrace()
            }

            return interestingPhotoList
        }

        companion object {
            private const val TAG = "FetchPhotosTask"
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

//    class FetchInterestingPhotoListAsyncTask extends AsyncTask<String, Void, List<PhotoItem>>{
//
//        @Override
//        protected List<InterestingPhoto> doInBackground(String... strings){
//            String url = strings[0];
//            List<PhotoItem> interestingPhotoList = new ArrayList<>();
//
//            try{
//                // opening url request
//                URL urlObject = new Url(url);
//                HttpsURLConnection urlConnection = (HttpsUrlConnection) urlObject.openConnection();
//                // downlanding json response
//                String jsonREsult = "";
//                InputStream in = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in);
//                int data = reader.read();
//                while (data != -1){
//                    jsonResult += (char) data;
//                    data = reader.read();
//                }
//                Log.d(TAG, "doInBackground: " + jsonResult)
//
//                //parse the json
//                JSONObject jsonObject = new JSONObject(jsonResult);
//                JSONObject photosObject = photosObject.getJSONArray("photo");
//                JSONArray photoArray = photosObject.getJSONArray("photos");
//                for (int i=0;i<photoArray.length();i++){
//                    JSONObject singlePhotoObject = photoArray.getJSONOBJECT(i);
//                    PhotoItem photoItem = parceInterestingPhoto(singlePhotoObject);
//                    if(photoItem != null){
//                        interestingPhotoList.add(photoItem)
//                    }
//                }
//
//            } catch (MalformedURLException e){
//                e.printStackTree();
//            } catch (IOException e){
//                e.printStackTree();
//            } catch (JSONException e){
//                e.printStackTree();
//            }
//
//            return interestingPhotoList;
//        }
//    }
}