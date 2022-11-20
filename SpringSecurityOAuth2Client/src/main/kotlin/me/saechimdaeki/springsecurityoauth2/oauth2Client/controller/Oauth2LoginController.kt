package me.saechimdaeki.springsecurityoauth2.oauth2Client.controller

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class Oauth2LoginController {

    @GetMapping("/oauth2Login")
    fun oauth2Login(model:Model, request:HttpServletRequest, response: HttpServletResponse): String {
        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(authentication: Authentication, request: HttpServletRequest, response: HttpServletResponse) :String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)
        return "redirect:/"
    }
}