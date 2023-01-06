package me.saechimdaeki.authorizationserver

import org.springframework.security.core.Authentication

data class OpaqueDto(
    val active: Boolean,
    val authentication: Authentication,
    val principal: Any
) {
}