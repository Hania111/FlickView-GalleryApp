package com.example.flickview_galleryapp.ui.screen

import android.provider.ContactsContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.flickview_galleryapp.FlickrViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import com.example.flickview_galleryapp.PhotoItem
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.flickview_galleryapp.R
import com.example.flickview_galleryapp.UserInputEvents
import java.time.format.TextStyle


@Composable
fun HomeScreen(navController: NavHostController, flickrViewModel : FlickrViewModel) {

    val viewModel: FlickrViewModel = viewModel()
    val photos by viewModel.photosStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPhotos()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.gallery_app),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        if (photos.isEmpty()) {
            Text(stringResource(R.string.loading_photos))
        } else {
            PhotosList(photos = photos, navController, flickrViewModel)
        }
    }
}

@Composable
fun PhotosList(photos: List<PhotoItem>, navController: NavHostController, flickrViewModel : FlickrViewModel) {
    val configuration = LocalConfiguration.current
    val imageSize = configuration.screenWidthDp.dp / 3
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(photos) { photo ->
                Image(
                    painter = rememberImagePainter(
                        data = photo.photoUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = photo.title,
                    modifier = Modifier
                        .size(imageSize)
                        .padding(1.dp)
                        .clickable {
                            flickrViewModel.onEvent(UserInputEvents.SelectedPhotoItem(photo))
                            navController.navigate(Routes.PICTURE_DESCRIPTION_SCREEN)
                        },
                    contentScale = ContentScale.Crop,

                )
            }
        }
    )
}

