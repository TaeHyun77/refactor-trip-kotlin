package com.example.kotlinPro.refresh

import com.example.kotlinPro.config.CreateCookie
import com.example.kotlinPro.jwt.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ReTokenService(
    private val jwtUtil: JwtUtil,
    private val refreshRepository: RefreshRepository
) {

    @Transactional
    fun reToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val cookies = request.cookies
        val refresh = cookies?.firstOrNull { it.name == "refresh" }?.value

        if (refresh == null) {
            return ResponseEntity("refresh token null", HttpStatus.BAD_REQUEST)
        }

        try {
            jwtUtil.isExpired(refresh)
        } catch (e: ExpiredJwtException) {
            return ResponseEntity("access token expired", HttpStatus.BAD_REQUEST)
        }

        val category = jwtUtil.getCategory(refresh)
        if (category != "refresh") {
            return ResponseEntity("invalid refresh token", HttpStatus.BAD_REQUEST)
        }

        val isExist = refreshRepository.existsByRefresh(refresh)
        if (!isExist) {
            return ResponseEntity("invalid refresh token", HttpStatus.BAD_REQUEST)
        }

        val username = jwtUtil.getUsername(refresh)
        val role = jwtUtil.getRole(refresh)
        val name = jwtUtil.getName(refresh)
        val email = jwtUtil.getEmail(refresh)
        val password = jwtUtil.getPassword(refresh)
        val gender = jwtUtil.getGender(refresh)
        val age = jwtUtil.getAge(refresh)

        val newAccess = jwtUtil.createToken(
            username = username,
            password = password,
            name = name,
            email = email,
            role = role.name,
            gender = gender,
            age = age,
            category = "access",
            expired = 600_000L,
        )

        val newRefresh = jwtUtil.createToken(
            username = username,
            password = password,
            name = name,
            email = email,
            role = role.name,
            gender = gender,
            age = age,
            category = "refresh",
            expired = 86_400_000L,
        )

        refreshRepository.deleteByRefresh(refresh)
        createRefresh(username, newRefresh, 86_400_000L)

        response.setHeader("access", newAccess)
        response.addCookie(CreateCookie.createCookie("refresh", newRefresh))

        return ResponseEntity.ok().build()
    }

    @Transactional
    fun createRefresh(username: String, refresh: String, expired: Long) {
        val refresh = Refresh(username = username, refresh = refresh, expiration = expired)

        refreshRepository.save(refresh)
    }
}