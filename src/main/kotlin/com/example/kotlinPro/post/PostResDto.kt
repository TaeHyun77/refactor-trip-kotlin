package com.example.kotlinPro.post

import com.example.kotlinPro.member.Member
import com.example.kotlinPro.member.MemberResDto
import java.time.LocalDateTime

data class PostResDto(
    val id: Long?,

    var title: String,

    var content: String,

    val writer: String,

    var mbti: String?,

    var place: String?,

    var people: Int?,

    var viewCnt: Int?,

    var postCategory: String,

    var status: Boolean = false,

    var postImage: String?,

    val createdAt: LocalDateTime?,

    var modifiedAt: LocalDateTime?,

    val travelStartDate: String?,

    val travelEndDate: String?,

    val member: MemberResDto? = null
) {
}