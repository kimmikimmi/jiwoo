package com.jworkplace.demo

import com.jworkplace.demo.auth.RequestTokenAuthInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfig: WebMvcConfigurer {

    @Autowired
    lateinit var requestTokenAuthInterceptor: RequestTokenAuthInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestTokenAuthInterceptor)
    }
}