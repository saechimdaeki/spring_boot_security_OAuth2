package me.saechimdaeki.springsecurityoauth2.oauth2Client.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Oauth2HomeController {

    @GetMapping("/home")
    fun home() = "home"


}