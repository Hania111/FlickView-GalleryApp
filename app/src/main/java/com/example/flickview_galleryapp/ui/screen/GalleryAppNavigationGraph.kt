package com.example.flickview_galleryapp.ui.screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flickview_galleryapp.FlickrViewModel

@Composable
fun GalleryAppNaviagationGraph(flickrViewModel : FlickrViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN){
        composable(Routes.HOME_SCREEN){
            HomeScreen(navController, flickrViewModel )
        }
        composable(Routes.PICTURE_DESCRIPTION_SCREEN){
            PictureDescriptionScreen(flickrViewModel )
        }
    }
}