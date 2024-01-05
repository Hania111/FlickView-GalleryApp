package com.example.flickview_galleryapp.ui.screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flickview_galleryapp.FlickrViewModel
import com.example.flickview_galleryapp.network.FlickrApi


@Composable
fun GalleryAppNaviagationGraph(flickrViewModel : FlickrViewModel = viewModel())
{
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

//class flickerApiInjection{
//    companion object {
//        fun provideFactory(flickrApi: FlickrApi): ViewModelProvider.Factory {
//            return object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
//                        @Suppress("UNCHECKED_CAST")
//                        return FlickrViewModel(flickrApi) as T
//                    }
//                    throw IllegalArgumentException("Unknown ViewModel class")
//                }
//            }
//        }
//    }
//
//}



