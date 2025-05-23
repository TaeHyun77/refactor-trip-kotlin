package com.example.kotlinPro.tripException

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(TripException::class)
    fun handleCustom400Exception(ex: TripException): ResponseEntity<ErrorDto> =
        ErrorDto.toResponseEntity(ex)

}