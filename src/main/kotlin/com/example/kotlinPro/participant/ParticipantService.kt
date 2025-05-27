package com.example.kotlinPro.participant

import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.post.PostRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository
) {

    fun participate(postId: Long, participantReqDto: ParticipantReqDto) {
        val post = postRepository.findById(postId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
            }

        val member = memberRepository.findByUsername(participantReqDto.username)
            ?: run{
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        val participant = participantReqDto.toParticipant(post, member)

        participantRepository.save(participant)
    }

    fun cancelParticipant(postId: Long, username: String) {

        val participant = participantRepository.findByPostIdAndUsername(postId, username)

        participantRepository.delete(participant)
    }

}