package com.example.kotlinPro.member

data class MemberResDto (
    val username: String,
    val role: Role,
    val name: String,
    val email: String,
    val gender: String,
    val age: String,
    val selfIntro: String?,
    val profileImage: String?
)
{}