package com.example.kotlinPro.participant

import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.post.PostRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository
) {

    // 참가
    @Transactional
    fun participate(postId: Long, participantReqDto: ParticipantReqDto) {

        val post = postRepository.findById(postId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
            }

        val member = memberRepository.findByUsername(participantReqDto.username)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)

        val participant = participantReqDto.toParticipant(post, member)

        participantRepository.save(participant)
    }

    // 참가 취소
    @Transactional
    fun cancelParticipant(postId: Long, username: String) {

        val participant = participantRepository.findByPostIdAndUsername(postId, username)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.PARTICIPANT_NOT_FOUND)

        participantRepository.delete(participant)
    }

}