package com.example.kotlinPro.tripException

import org.springframework.http.HttpStatus

class TripException(
    val status: HttpStatus,
    val errorCode: ErrorCode,
    val details: String = ""
): RuntimeException(details) {
}