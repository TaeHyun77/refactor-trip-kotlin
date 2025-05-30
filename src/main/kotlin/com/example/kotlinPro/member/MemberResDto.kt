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
    val selfIntro: String? = null,
    val profileImage: String? = null,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val followers: List<String>? = null,
    val followings: List<String>? = null,
    val messageList: List<MessageResDto>? = null
)
{}