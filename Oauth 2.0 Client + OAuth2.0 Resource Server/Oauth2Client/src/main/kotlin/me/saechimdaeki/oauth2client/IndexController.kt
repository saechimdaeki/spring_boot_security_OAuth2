package me.saechimdaeki.oauth2client

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    fun index() = "index"

    @GetMapping("/")
    fun home() = "home"
}