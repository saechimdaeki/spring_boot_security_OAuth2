package io.security.corsclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CorsClientApplication

fun main(args: Array<String>) {
    runApplication<CorsClientApplication>(*args)
}
