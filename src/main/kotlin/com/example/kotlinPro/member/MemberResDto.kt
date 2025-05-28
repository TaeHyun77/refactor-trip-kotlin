package com.example.kotlinPro.member

import com.example.kotlinPro.message.Message
import com.example.kotlinPro.message.MessageResDto
import java.time.LocalDateTime

data class MemberResDto (
    val id: Long?,
    val username: String,
    val role: Role,
    val name: String,
    val email: String,
    val gender: String,
    val age: String,
    val selfIntro: String?,
    val profileImage: String?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
    val followers: List<String>?,
    val followings: List<String>?,
    val messageList: List<MessageResDto>?
)
{}