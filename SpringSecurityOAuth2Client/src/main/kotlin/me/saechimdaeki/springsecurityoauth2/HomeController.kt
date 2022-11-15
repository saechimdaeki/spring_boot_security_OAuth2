package me.saechimdaeki.springsecurityoauth2

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/home")
    fun home() = "home"


}