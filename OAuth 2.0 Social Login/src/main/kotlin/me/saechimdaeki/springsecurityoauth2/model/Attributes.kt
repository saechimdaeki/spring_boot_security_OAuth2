package me.saechimdaeki.springsecurityoauth2.model

data class Attributes(
    val mainAttributes: MutableMap<String, Any> = mutableMapOf(),
    val subAttributes: MutableMap<String, Any> = mutableMapOf(),
    val otherAttributes: MutableMap<String, Any> = mutableMapOf(),
)