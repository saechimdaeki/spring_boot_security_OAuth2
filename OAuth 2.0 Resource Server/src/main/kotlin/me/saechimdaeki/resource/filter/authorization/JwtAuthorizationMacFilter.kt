package me.saechimdaeki.resource.filter.authorization

import com.nimbusds.jose.JWSVerifier


class JwtAuthorizationMacFilter(jwsVerifier: JWSVerifier) : JwtAuthorizationFilter(jwsVerifier){
    
}