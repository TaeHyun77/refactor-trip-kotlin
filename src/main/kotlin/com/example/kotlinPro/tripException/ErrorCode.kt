package com.example.kotlinPro.tripException

enum class ErrorCode (
    val errorCode: String,
    val message: String
){

    UNKNOWN("000_UNKNOWN", "알 수 없는 에러가 발생했습니다."),

    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다.")

}