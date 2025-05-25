package com.example.kotlinPro.config

import jakarta.servlet.http.Cookie

class CreateCookie() {

    companion object {
        fun createCookie(key: String, value: String): Cookie {

            // apply : 객체 생성 후 속성을 설정한 뒤 반환
            return Cookie(key, value).apply {
                maxAge = 12 * 60 * 60
                isHttpOnly = false
                path = "/"
            }
        }
    }
}