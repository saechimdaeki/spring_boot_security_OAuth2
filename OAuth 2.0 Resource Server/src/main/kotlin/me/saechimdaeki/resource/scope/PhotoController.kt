package me.saechimdaeki.resource.scope

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

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

    @GetMapping("/photos/3")
//    @PreAuthorize("hasAnyAuthority('SCOPE_photo')")
    fun photo3() : Photo {
        return Photo(photoId = "3", photoTitle = "Photo 3 title", photoDescription = "Photo3 is nice", userId = "user3")
    }

    @GetMapping("/")
    @ResponseBody
    fun  index(authentication: Authentication, @AuthenticationPrincipal principal: OAuth2AuthenticatedPrincipal) : Authentication{
        val bearerTokenAuthentication = authentication as BearerTokenAuthentication
        val tokenAttributes = bearerTokenAuthentication.tokenAttributes
        val boolean = tokenAttributes["active"] as Boolean
        OpaQueDto(active = boolean, authentication = authentication, principal =  principal)
        return authentication
    }
}