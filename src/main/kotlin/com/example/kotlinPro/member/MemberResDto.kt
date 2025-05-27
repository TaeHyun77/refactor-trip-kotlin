package com.example.kotlinPro.member

import java.time.LocalDateTime

data class MemberResDto (
    val username: String,
    val role: Role,
    val name: String,
    val email: String,
    val gender: String,
    val age: String,
    val selfIntro: String?,
    val profileImage: String?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?
)
{}