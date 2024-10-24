package io.hhplus.concertreservationservice.config

import io.hhplus.concertreservationservice.config.interceptor.AuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authInterceptor: AuthInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/**") // 인증이 필요한 경로 지정
            .excludePathPatterns("/api/auth/**") // 인증이 필요 없는 경로 제외
    }
}
