package me.saechimdaeki.springsecurityoauth2.converters

interface ProviderUserConverter<T, R> {
    fun converter(t: T): R?
}