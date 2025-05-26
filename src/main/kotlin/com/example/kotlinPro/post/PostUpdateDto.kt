package com.example.kotlinPro.post

data class PostUpdateDto(
    val title: String,
    val content: String,
    val mbti: String?,
    val place: String?,
    val postImage: String?,
    val travelStartDate: String?,
    val travelEndDate: String?
)