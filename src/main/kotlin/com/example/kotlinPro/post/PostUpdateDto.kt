package com.example.kotlinPro.post

data class PostUpdateDto(
    val id: Long,
    val title: String,
    val content: String,
    val mbti: String,
    val place: String,
    val viewCnt: Int,
    val people: Int,
    val status: Boolean
)