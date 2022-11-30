package me.saechimdaeki.springsecurityoauth2.controller

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class LoginController {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?): String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)
        return "redirect:/login"
    }
}