package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.model.FormUser
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser


class UserDetailsProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    override fun converter(t: ProviderUserRequest): ProviderUser? {
        val user = t.user
        return user?.let {
            FormUser(
                formUserId = it.id!!,
                formPassword = it.password!!,
                formEmail = it.email!!,
                formAuthorities = it.authorities!!,
                formProvider = "none",
                formUserName = it.username!!
            )
        }
    }
}