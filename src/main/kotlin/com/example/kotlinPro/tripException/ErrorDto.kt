package com.example.kotlinPro.tripException

import org.springframework.http.ResponseEntity

class ErrorDto(
    val code: String,
    val message: String,
    val detail: String
) {

    companion object {
        fun toResponseEntity(ex: TripException): ResponseEntity<ErrorDto> {
            val errorType: ErrorCode = ex.errorCode
            val detail: String = ex.details

            return ResponseEntity
                .status(ex.status)
                .body(
                    ErrorDto(
                        code = errorType.errorCode,
                        message = errorType.message,
                        detail = detail
                    )
                )
        }
    }
}