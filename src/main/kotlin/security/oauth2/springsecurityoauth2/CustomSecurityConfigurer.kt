//package security.oauth2.springsecurityoauth2
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
//
//class CustomSecurityConfigurer : AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity>() {
//
//    var isSecure = false
//
//
//    override fun init(builder: HttpSecurity?) {
//        super.init(builder)
//        println("init method started..")
//    }
//
//    override fun configure(builder: HttpSecurity?) {
//        super.configure(builder)
//        println("configure method started..")
//        when {
//            isSecure -> {
//                println("https is required")
//            }
//            else -> {
//                println("https is optional")
//            }
//        }
//    }
//
//    fun setFlag(isSecure: Boolean): CustomSecurityConfigurer {
//        this.isSecure = isSecure
//        return this
//    }
//}
