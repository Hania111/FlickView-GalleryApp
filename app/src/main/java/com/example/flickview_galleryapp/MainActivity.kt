package com.example.flickview_galleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flickview_galleryapp.ui.theme.FlickViewGalleryAppTheme
import com.example.flickview_galleryapp.ui.screen.GalleryAppNaviagationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickViewGalleryAppTheme {
                GalleryApp()
            }
        }
    }

    @Composable
    fun GalleryApp(){
        GalleryAppNaviagationGraph()
    }
}



