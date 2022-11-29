package me.saechimdaeki.springsecurityoauth2.enums

class OAuth2Config {
    enum class SocialType(val socialName: String) {
        GOOGLE("google"),
        APPLE("apple"),
        FACEBOOK("facebook"),
        NAVER("naver"),
        KAKAO("kakao");

    }
}