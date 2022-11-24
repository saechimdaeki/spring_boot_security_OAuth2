package me.saechimdaeki.springsecurityoauth2

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class Oauth2ClientConfig(

    // TODO 구현
//    private val customOAuth2UserService: CustomOauth2UserService,
//    private val customOidcUserService: CustomOidcuserService,
) {

    @Bean
    fun webSecurityCustomizer() : WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring().antMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**");
        }
    }

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http.authorizeRequests { requests -> requests
            .antMatchers("/api/user")
            .access("hasAnyRole('SCOPE_profile','SCOPE_email')")
            .antMatchers("/api/oidc")
            .access("hasRole('SCOPE_openid')")
            .antMatchers("/")
            .permitAll()
            .anyRequest().authenticated()
        }
        http.oauth2Login { oauth2 -> oauth2.userInfoEndpoint{
            userInfoEndpointConfig -> userInfoEndpointConfig
//            .userService { customOAuth2UserService }
//            .oidcUserService { customOidcUserService }
        } }
        http.logout().logoutSuccessUrl("/")
        return http.build()
    }

    @Bean
    fun customAuthorityMapper() : GrantedAuthoritiesMapper {
        return CustomAuthorityMapper()
    }

}

