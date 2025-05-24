package com.example.kotlinPro.post

import com.example.kotlinPro.BaseTime
import com.example.kotlinPro.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var content: String,

    val writer: String,

    var mbti: String,

    var place: String,

    // 조회수
    var viewCnt: Int,

    var people: Int,

    // 동행글 or 자유글
    val category: String,

    var status: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    ): BaseTime() {

    fun editPost(title: String, content: String, mbti: String, place: String, viewCnt: Int, people: Int, status: Boolean) {
        this.title = title
        this.content = content
        this.mbti = mbti
        this.place = place
        this.viewCnt = viewCnt
        this.people = people
        this.status = status
    }
}