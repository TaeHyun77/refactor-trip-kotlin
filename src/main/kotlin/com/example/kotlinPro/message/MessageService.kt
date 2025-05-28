package com.example.kotlinPro.message

import com.example.kotlinPro.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val memberRepository: MemberRepository,
    private val messageRepository: MessageRepository
) {

    fun sendMessage(messageReqDto: MessageReqDto) {
        val receiveMember = memberRepository.findByUsername(messageReqDto.receiver)

        val message = messageReqDto.toMessage(receiveMember)

        messageRepository.save(message)
    }

    fun messageList(): List<MessageResDto> {

        val list: List<Message> = messageRepository.findAll()

        return list.map{message ->
            MessageResDto(
                receiver = message.receiver,
                sender = message.sender,
                title = message.title,
                content = message.content,
                createdAt = message.createdAt,
                modifiedAt = message.modifiedAt
            )
        }
    }
}