package com.example.kotlinPro.member

data class MemberUpdateDto(

    val username: String,
    val name: String,
    val email: String,
    val gender: String,
    val age: String,
    val selfIntro: String
) {
}