package me.saechimdaeki.springsecurityoauth2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @GetMapping("/loginPage")
    fun loginPage() = "loginPage"
}