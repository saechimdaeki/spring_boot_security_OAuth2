package io.security.corsserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CorsServerApplication

fun main(args: Array<String>) {
    runApplication<CorsServerApplication>(*args)
}
