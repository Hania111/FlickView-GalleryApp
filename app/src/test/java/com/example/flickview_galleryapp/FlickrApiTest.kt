package com.example.flickview_galleryapp

import com.example.flickview_galleryapp.network.FlickrApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection

class FlickrApiTest {
    private lateinit var flickrApi: FlickrApi

    private fun createSampleAuthor(): PhotoAuthor{
        return PhotoAuthor(
            username = "exampleUsername",
            fullname = "Example Fullname")
    }

    private fun createSamplePhotoList(): List<PhotoItem>{
        return listOf(
            PhotoItem(
                id = "12345",
                title = "A beautiful landscape",
                dateTaken = "2024-01-01",
                photoUrl = "https://example.com/photo1.jpg",
                owner = "owner1"
            ),
            PhotoItem(
                id = "67890",
                title = "City night lights",
                dateTaken = "2024-01-02",
                photoUrl = "https://example.com/photo2.jpg",
                owner = "owner2"
            )
        )
    }


    @Before
    fun setUp() {
        flickrApi = mock(FlickrApi::class.java)
    }

    @Test
    fun fetchInterestingPhotos_returnsPhotos() = runBlocking {
        `when`(flickrApi.fetchInterestingPhotos()).thenReturn(createSamplePhotoList())
        val photosList = flickrApi.fetchInterestingPhotos()
        assertNotNull(photosList)
        assertTrue("List should contain PhotoItem objects", photosList.all { it is PhotoItem })
    }

    @Test
    fun fetchAuthorInformation_returnsAuthor() = runBlocking {
        val authorId = "someAuthorId"
        `when`(flickrApi.fetchAuthorInformation(authorId)).thenReturn(createSampleAuthor())
        val author = flickrApi.fetchAuthorInformation(authorId)
        assertNotNull("Author should not be null", author)
        assertTrue("Should return a PhotoAuthor object", author is PhotoAuthor)
        assertEquals("Username should match", "exampleUsername", author?.username)
        assertEquals("Fullname should match", "Example Fullname", author?.fullname)
    }
}