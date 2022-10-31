package io.security.corsserver

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CorsServerController {

    @GetMapping("/users")
    fun users() : User = User("user",20)
}