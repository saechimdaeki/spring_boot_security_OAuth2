package me.saechimdaeki.resource.scope

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PhotoController {

    @GetMapping("/photos/1")
    fun photo1() : Photo {
        return Photo(photoId = "1", photoTitle = "Photo 1 title", photoDescription = "Photo1 is nice", userId = "user1")
    }

    @GetMapping("/photos/2")
//    @PreAuthorize("hasAnyAuthority('SCOPE_photo')")
    fun photo2() : Photo {
        return Photo(photoId = "2", photoTitle = "Photo 2 title", photoDescription = "Photo2 is nice", userId = "user2")
    }
}