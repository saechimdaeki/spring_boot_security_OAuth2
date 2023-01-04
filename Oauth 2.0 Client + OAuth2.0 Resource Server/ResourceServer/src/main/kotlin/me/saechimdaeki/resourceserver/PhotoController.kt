package me.saechimdaeki.resourceserver

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotoController {

    @GetMapping("/photos")
    fun photos(): List<Photo> {

        val photo1 : Photo = getPhoto("1","Photo 1 Title", "Photo is nice", "user1")
        val photo2 : Photo = getPhoto("2","Photo 2 Title", "Photo is beautiful", "user2")
        return listOf(photo1,photo2)
    }


    @GetMapping("/remotePhotos")
    fun remotePhotos(): List<Photo> {

        val photo1 : Photo = getPhoto("1","remote Photo 1 Title", "remote Photo is nice", "user1")
        val photo2 : Photo = getPhoto("2","remote Photo 2 Title", "remote Photo is beautiful", "user2")
        return listOf(photo1,photo2)
    }

    private fun getPhoto(photoId: String, photoTitle: String, photoDesc: String, userId: String): Photo {
        return Photo(photoId = photoId, photoTitle = photoTitle, photoDescription =  photoDesc, userId = userId)
    }
}