package me.saechimdaeki.resource.macAndRsa.filter.authorization

import com.nimbusds.jose.JWSVerifier


class JwtAuthorizationRsaFilter(jwsVerifier: JWSVerifier) : JwtAuthorizationFilter(jwsVerifier){

}