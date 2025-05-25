package com.example.kotlinPro.jwt

import com.example.kotlinPro.member.Member
import com.example.kotlinPro.member.Role
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtFilter(
    private val jwtUtil: JwtUtil
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val accessToken = request.getHeader("access") ?: run {
            filterChain.doFilter(request, response)
            return
        }

        try {
            if (jwtUtil.isExpired(accessToken)) {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.ACCESSTOKEN_ISEXPIRED)
            }
        } catch (e: TripException) {
            throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_TOKEN)
        }

        val category = jwtUtil.getCategory(accessToken)
        if (category != "access") {
            throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.IS_NOT_ACCESSTOKEN)
        }

        val username: String = jwtUtil.getUsername(accessToken)
        val role: Role = jwtUtil.getRole(accessToken)

        val member = Member(username = username, role = role)

        val customUserDetails = CustomUserDetails(member)

        val authToken = UsernamePasswordAuthenticationToken(
            customUserDetails, null, customUserDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authToken

        filterChain.doFilter(request, response)
    }
}