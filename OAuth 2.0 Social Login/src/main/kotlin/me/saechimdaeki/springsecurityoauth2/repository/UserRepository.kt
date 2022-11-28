package me.saechimdaeki.springsecurityoauth2.repository

import me.saechimdaeki.springsecurityoauth2.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    private val users = mutableMapOf<String,Any>()

    fun findByUsername(userName:String) : User?{
        return users[userName]?.let {
            return it as User
        }
    }

    fun register(user: User) {
        if(users.containsKey(user.username)){
            return
        }
        users[user.username] = user
    }
}