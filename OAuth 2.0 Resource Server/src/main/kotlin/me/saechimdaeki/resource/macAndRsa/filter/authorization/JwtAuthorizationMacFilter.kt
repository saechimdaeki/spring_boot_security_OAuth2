package me.saechimdaeki.resource.macAndRsa.filter.authorization

import com.nimbusds.jose.JWSVerifier


class JwtAuthorizationMacFilter(jwsVerifier: JWSVerifier) : JwtAuthorizationFilter(jwsVerifier){
    
}