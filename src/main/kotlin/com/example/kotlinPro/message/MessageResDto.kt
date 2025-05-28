package com.example.kotlinPro.message

import java.time.LocalDateTime

class MessageResDto(
    val receiver: String,

    val sender: String,

    val title: String,

    val content: String,

    val createdAt: LocalDateTime?,

    val modifiedAt: LocalDateTime?
) {
}