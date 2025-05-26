package com.example.kotlinPro.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// 컴퓨터 내 경로에 접근하기 위한 클래스, 기본적으로 Spring Boot는 resources/static 같은 클래스패스 경로만 서빙하기 때문 .. ..
/*
* addResourceHandler : 클라이언트가 접근할 URI 경로 지정
* addResourceLocations : 실제 파일이 존재하는 서버의 경로 지정 (file 접두사 중요함)
* */
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/profileImages/**", "/postImages/**")
            .addResourceLocations("file:${System.getProperty("user.home")}/profile-images/", "file:${System.getProperty("user.home")}/post-images/")
    }
}

