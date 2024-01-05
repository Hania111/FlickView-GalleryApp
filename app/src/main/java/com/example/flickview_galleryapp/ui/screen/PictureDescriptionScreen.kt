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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.flickview_galleryapp.FlickrViewModel
import com.example.flickview_galleryapp.PhotoAuthor
import com.example.flickview_galleryapp.PhotoItem
import com.example.flickview_galleryapp.R
import com.example.flickview_galleryapp.UserInputEvents
import com.example.flickview_galleryapp.ui.theme.AppTypography

@Composable
fun PictureDescriptionScreen(flickrViewModel : FlickrViewModel) {
    val selectedPhotoItem by flickrViewModel.selectedPhoto.collectAsState()
    val photoAuthor by flickrViewModel.authorInfo.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        selectedPhotoItem?.let {photoItem ->
            PrintPhoto(selectedPhotoItem = photoItem)
            PrintDescription(selectedPhotoItem = photoItem)
            photoAuthor?.let { author ->
                PrintAuthorDescription(photoAuthor = author)
            } ?: run {
                Text(text = stringResource(R.string.can_t_fetch_information_about_author),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = AppTypography.defaultTextStyle,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun PrintDescription(selectedPhotoItem: PhotoItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DescriptionItem(label = stringResource(R.string.title), text = selectedPhotoItem.title)
        DescriptionItem(label = stringResource(R.string.date_taken), text = selectedPhotoItem.dateTaken)

    }
}

@Composable
fun PrintAuthorDescription(photoAuthor : PhotoAuthor) {
    DescriptionItem(label = stringResource(R.string.author_username), text = photoAuthor.username)
    DescriptionItem(label = stringResource(R.string.author_name), text = photoAuthor.fullname)
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
            style = AppTypography.defaultTextStyle
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
    )
}
