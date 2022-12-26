package me.saechimdaeki.resource.filter.authorization

import com.nimbusds.jose.JWSVerifier


class JwtAuthorizationRsaFilter(jwsVerifier: JWSVerifier) : JwtAuthorizationFilter(jwsVerifier){

}