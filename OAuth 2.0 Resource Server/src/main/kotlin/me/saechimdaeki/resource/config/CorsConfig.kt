package me.saechimdaeki.resource.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true //인증 처리 허용
        config.addAllowedOrigin("*") //도메인 허용
        config.addAllowedHeader("*") // 헤더 허용
        config.addAllowedMethod("*") // http method 허용

        source.registerCorsConfiguration("/api/**",config)
        return CorsFilter(source)
    }
}