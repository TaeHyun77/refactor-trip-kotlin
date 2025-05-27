package com.example.kotlinPro.participant

import com.example.kotlinPro.member.Member
import com.example.kotlinPro.post.Post

data class ParticipantReqDto(
    val username: String
) {
    fun toParticipant(post: Post, member: Member): Participant {
        return Participant(
            username = username,
            post = post,
            member = member
        )
    }
}