package com.example.kotlinPro.member

data class MemberReqDto(
    val username: String,
    val password: String,
    val role: Role,
    val name: String,
    val email: String,
    val category: String,
    val gender: String,
    val age: String,
    val selfIntro: String
) {
    fun toMemberEntity(): Member {
        return Member(
            username = username,
            password = password,
            role = role,
            name = name,
            email = email,
            category = category,
            gender = gender,
            age = age,
            selfIntro = selfIntro
        )
    }
}