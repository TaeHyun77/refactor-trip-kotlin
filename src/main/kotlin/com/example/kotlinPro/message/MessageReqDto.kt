package com.example.kotlinPro.message

import com.example.kotlinPro.member.Member

data class MessageReqDto(
    val receiver: String,

    val sender: String,

    var title: String,

    var content: String,

) {

    fun toMessage(member: Member): Message {
        return Message(
            receiver = receiver,
            sender = sender,
            title = title,
            content = content,
            member = member
        )
    }
}