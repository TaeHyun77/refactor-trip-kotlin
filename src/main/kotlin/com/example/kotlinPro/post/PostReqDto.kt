package com.example.kotlinPro.post

import com.example.kotlinPro.member.Member

data class PostReqDto (

    val id: Long,

    var title: String,

    var content: String,

    val writer: String,

    var mbti: String,

    var place: String,

    var viewCnt: Int,

    var people: Int,

    var category: String,

    var status: Boolean = false,
    ){
    fun toPostEntity(member: Member): Post{
        return Post(
            title = title,
            content = content,
            writer = writer,
            mbti = mbti,
            place = place,
            viewCnt = viewCnt,
            people = people,
            category = category,
            status = status,
            member = member
        )
    }
}