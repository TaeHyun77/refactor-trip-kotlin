package com.example.kotlinPro.refresh

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ReTokenController(
    private val reTokenService: ReTokenService
) {

    @PostMapping("/reToken")
    fun reToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        return reTokenService.reToken(request, response)
    }

}