package com.example.kotlinPro.post

import com.example.kotlinPro.member.Member
import java.time.LocalDate

data class PostReqDto (

    val id: Long,

    var title: String,

    var content: String,

    val writer: String,

    var mbti: String?,

    var place: String?,

    var people: Int?,

    var viewCnt: Int?,

    var postCategory: String,

    val travelStartDate: String?,

    val travelEndDate: String?
    ){

    fun toPostEntity(member: Member): Post{
        return Post(
            title = title,
            content = content,
            writer = writer,
            mbti = mbti,
            place = place,
            people = people,
            viewCnt = 0,
            postCategory = postCategory,
            member = member,
            travelStartDate = travelStartDate,
            travelEndDate = travelEndDate
        )
    }
}