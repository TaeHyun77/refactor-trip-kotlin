package com.example.kotlinPro.member

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

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
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다: ${memberUpdateDto.username}")

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
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다: $username")

        memberRepository.delete(deleteMember)

    }
}