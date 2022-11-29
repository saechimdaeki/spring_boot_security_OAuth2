package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
class DelegatingProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    private lateinit var converters: List<ProviderUserConverter<ProviderUserRequest, ProviderUser>>

    init {
        val providerUserConverters = listOf(
            OAuth2GoogleProviderUserConverter(),
            OAuth2NaverProviderUserConverter()
        )
        this.converters = providerUserConverters
    }

    override fun converter(t: ProviderUserRequest): ProviderUser? {
        Assert.notNull(t, "providerUserRequest cannot be null")

        for (converter in converters) {
            val providerUser = converter.converter(t)
            if (providerUser != null) return providerUser
        }
        return null
    }
}