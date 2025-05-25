package com.example.kotlinPro.config

import com.example.kotlinPro.jwt.JwtFilter
import com.example.kotlinPro.jwt.JwtUtil
import com.example.kotlinPro.jwt.LoginFilter
import com.example.kotlinPro.jwt.CustomLogoutFilter
import com.example.kotlinPro.refresh.RefreshRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class SecurityConfig(
    // 인증 처리를 담당하는 핵심 컴포넌트
    // 기본적으로 Security는 내부에서 AuthenticationManager를 자동으로 설정하지만
    // , 커스텀 로그인 필터( LoginFilter, LogoutFilter ) 등을 만들 때는 아래처럼 명시적으로 선언해야 함
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val jwtUtil: JwtUtil,
    private val refreshRepository: RefreshRepository
) {

    @Bean
    fun authenticationManager(): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    // Spring Security 5 이상에서는 비밀번호는 반드시 인코딩되어야함
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/", "/member/**", "post"
                    ).permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/login", "/logout").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtFilter(jwtUtil), LoginFilter::class.java)
            .addFilterAt(LoginFilter(authenticationManager(), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(CustomLogoutFilter(jwtUtil, refreshRepository), JwtFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("*")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")
        configuration.exposedHeaders = listOf("Authorization")
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}