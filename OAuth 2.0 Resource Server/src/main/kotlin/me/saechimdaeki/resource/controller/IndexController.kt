package me.saechimdaeki.resource.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping("/api/user")
    fun index(authentication: Authentication) :Authentication {
        return authentication
    }

}