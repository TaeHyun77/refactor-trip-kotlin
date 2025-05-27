package com.example.kotlinPro.comment

import java.time.LocalDateTime

data class CommentResDto(

    val id: Long?,

    val content: String,

    val writer: String?,

    val createdAt: LocalDateTime?,

    val modifiedAt: LocalDateTime?
) {
}