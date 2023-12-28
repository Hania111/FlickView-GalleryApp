package com.example.flickview_galleryapp.ui.screen

import android.provider.ContactsContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.flickview_galleryapp.FlickrViewModel

@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: FlickrViewModel = viewModel()

    // This will recompose whenever the observed data changes
    //val photos by viewModel.photosStateFlow.collectAsState()

    // Fetch photos when the composable enters the composition
    LaunchedEffect(Unit) {
        viewModel.fetchPhotos()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Gallery App",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

//@Composable
//fun PhotosList(photos: List<ContactsContract.CommonDataKinds.Photo>) {
//    LazyVerticalGrid(
//        cells = GridCells.Fixed(3),
//        content = {
//            items(photos) { photo ->
//                Image(
//                    painter = rememberImagePainter(
//                        data = photo.url, // Use the correct image URL property from your Photo class
//                        builder = {
//                            crossfade(true)
//                        }
//                    )
//                )
//            }
//        }
//    )
//}