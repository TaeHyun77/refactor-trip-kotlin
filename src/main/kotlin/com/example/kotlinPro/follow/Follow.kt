package com.example.kotlinPro.follow

import com.example.kotlinPro.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Follow(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    val follower: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    val following: Member,
){
}