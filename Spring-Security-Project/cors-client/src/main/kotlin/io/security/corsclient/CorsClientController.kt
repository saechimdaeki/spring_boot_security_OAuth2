package io.security.corsclient

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CorsClientController {

    @GetMapping("/")
    fun index() : String = "index"
}