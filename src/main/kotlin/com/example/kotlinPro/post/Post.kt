package com.example.kotlinPro.post

import com.example.kotlinPro.BaseTime
import com.example.kotlinPro.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var content: String,

    val writer: String,

    var mbti: String?,

    var place: String?,

    // 조회수
    var viewCnt: Int?,

    @Column(nullable = true)
    var people: Int? = null,

    // 동행글 or 자유글
    val postCategory: String,

    var status: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    var postImage: String? = null,

    var travelStartDate: String?,

    var travelEndDate: String?

    ): BaseTime() {

        fun updatePost(title: String, content: String, mbti: String?, place: String?, postImage: String?, travelStartDate: String?, travelEndDate: String?) {
            this.title = title
            this.content = content
            this.mbti = mbti
            this.place = place
            this.postImage = postImage
            this.travelStartDate = travelStartDate
            this.travelEndDate = travelEndDate
        }
    }