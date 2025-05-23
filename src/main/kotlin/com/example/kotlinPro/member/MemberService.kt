package com.example.kotlinPro.member

import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class MemberService(
    private val memberRepository: MemberRepository

) {

    fun registerMember(memberReqDto: MemberReqDto) {

        memberRepository.save(memberReqDto.toMemberEntity())

    }

    // member 수정
    @Transactional
    fun updateMember(memberUpdateDto: MemberUpdateDto) {

        val member = memberRepository.findByUsername(memberUpdateDto.username)
            ?: run {
                log.warn { "회원 정보를 찾을 수 없습니다. (수정 실패) username: ${memberUpdateDto.username}" }
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        member.updateMember(
            memberUpdateDto.name,
            memberUpdateDto.email,
            memberUpdateDto.gender,
            memberUpdateDto.age,
            memberUpdateDto.selfIntro
        )

        memberRepository.save(member)
    }

    @Transactional
    fun deleteMember(username: String) {

        val deleteMember = memberRepository.findByUsername(username)
            ?: run {
                log.warn { "회원 정보를 찾을 수 없습니다. (삭제 실패) username: $username"}
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        memberRepository.delete(deleteMember)

    }
}