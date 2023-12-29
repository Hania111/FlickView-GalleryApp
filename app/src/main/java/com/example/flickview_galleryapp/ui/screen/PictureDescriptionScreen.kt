package com.example.flickview_galleryapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.flickview_galleryapp.FlickrViewModel
import com.example.flickview_galleryapp.PhotoItem
import com.example.flickview_galleryapp.UserInputEvents

@Composable
fun PictureDescriptionScreen(flickrViewModel : FlickrViewModel) {
    val selectedPhotoItem by flickrViewModel.selectedPhoto.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        selectedPhotoItem?.let {photoItem ->
            PrintPhoto(selectedPhotoItem = photoItem)
            PrintDescription(selectedPhotoItem = photoItem)
        }
    }
}

@Composable
fun PrintDescription(selectedPhotoItem: PhotoItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DescriptionItem(label = "Title", text = selectedPhotoItem.title)
        DescriptionItem(label = "Date Taken", text = selectedPhotoItem.dateTaken)
        DescriptionItem(label = "ID", text = selectedPhotoItem.id)
    }
}

@Composable
fun DescriptionItem(label: String, text: String?) {
    text?.let {
        Text(
            text = "$label: $it",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
    }
}

@Composable
fun PrintPhoto(selectedPhotoItem: PhotoItem){
    val maxImageHeight = 0.6f
    Image(
        painter = rememberImagePainter(
            data = selectedPhotoItem.photoUrl,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = selectedPhotoItem.title,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(maxImageHeight)
            .aspectRatio(1f)
        //    .fillMaxSize()
        //.padding(16.dp),
    )
}
