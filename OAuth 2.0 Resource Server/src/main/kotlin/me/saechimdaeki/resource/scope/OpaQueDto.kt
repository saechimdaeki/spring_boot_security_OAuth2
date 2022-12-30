package me.saechimdaeki.resource.scope

import org.springframework.security.core.Authentication

data class OpaQueDto(
    val active: Boolean,
    val authentication: Authentication,
    val principal:Any,
) {
}