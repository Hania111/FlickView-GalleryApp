package com.example.flickview_galleryapp.ui.screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.Composable

@Composable
fun GalleryAppNaviagationGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN){
        composable(Routes.HOME_SCREEN){
            HomeScreen(navController)
        }
        composable(Routes.PICTURE_DESCRIPTION_SCREEN){
            PictureDescriptionScreen()
        }
    }
}