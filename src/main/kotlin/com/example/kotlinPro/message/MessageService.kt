package com.example.kotlinPro.message

import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    private val memberRepository: MemberRepository,
    private val messageRepository: MessageRepository
) {

    // 쪽지 전송
    @Transactional
    fun sendMessage(messageReqDto: MessageReqDto) {
        val receiveMember = memberRepository.findByUsername(messageReqDto.receiver)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)

        val message = messageReqDto.toMessage(receiveMember)

        messageRepository.save(message)
    }

    // 쪽지 리스트 조회
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