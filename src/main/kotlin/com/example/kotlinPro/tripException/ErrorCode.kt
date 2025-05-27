package com.example.kotlinPro.tripException

enum class ErrorCode (
    val errorCode: String,
    val message: String
){

    UNKNOWN("000_UNKNOWN", "알 수 없는 에러가 발생했습니다."),

    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다."),

    POST_NOT_FOUND("MEMBER_NOT_FOUND", "게시글을 찾을 수 없습니다."),

    COMMENT_NOT_FOUND("COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다."),

    ACCESSTOKEN_ISEXPIRED("ACCESSTOKEN_ISEXPIRED", "토큰이 만료되었습니다."),

    IS_NOT_ACCESSTOKEN("IS_NOT_ACCESSTOKEN", "access 토큰이 아닙니다."),

    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다."),

    ROLE_CLAIM_MISSING("ROLE_CLAIM_MISSING", "유효하지 않은 권한입니다."),

    FILE_SAVE_FAILED("FILE_SAVE_FAILED", "이미지 파일 저장 실패")


}