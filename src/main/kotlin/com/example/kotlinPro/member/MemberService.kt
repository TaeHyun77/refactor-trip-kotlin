package com.example.kotlinPro.member

import com.example.kotlinPro.config.fileUploader
import com.example.kotlinPro.jwt.JwtUtil
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

private val log = KotlinLogging.logger {}

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {

    fun memberInfo(token: String): ResponseEntity<MemberResDto> {
        return try {
            val username = jwtUtil.getUsername(token)
            val role = jwtUtil.getRole(token)

            val member = memberRepository.findByUsername(username)
                ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)

            val dto = MemberResDto(
                username = username,
                role = role,
                name = member.name,
                email = member.email,
                gender = member.gender,
                age = member.age,
                selfIntro = member.selfIntro,
                profileImage = member.profileImage,
                createdAt = member.createdAt,
                modifiedAt = member.modifiedAt
            )

            ResponseEntity.ok(dto)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    fun registerMember(memberReqDto: MemberReqDto, file: MultipartFile?) {
        val encodedPassword = passwordEncoder.encode(memberReqDto.password)

        val member = memberReqDto.toMemberEntity(encodedPassword)

        if (file != null) {
            val imageUrl = fileUploader("profileImage", file)
            member.profileImage = imageUrl
        }

        memberRepository.save(member)
    }

    fun getProfileImage(username: String): ResponseEntity<ProfileImageDto> {

        val member = searchMember(username)

        val imagePath = member.profileImage  // ex) /profileImages/uuid_image.png
        log.info { "profileImage: ${member.profileImage}" }

        return ResponseEntity.ok(ProfileImageDto(profileImage = imagePath))
    }

    // member 수정
    @Transactional
    fun updateMember(memberUpdateDto: MemberUpdateDto) {

        val member = searchMember(memberUpdateDto.username)

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

        val member = searchMember(username)

        memberRepository.delete(member)
    }

    fun searchMember(username: String): Member {
        val member = memberRepository.findByUsername(username)
            ?: run {
                log.warn { "회원 정보를 찾을 수 없습니다. username: $username"}
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        return member
    }
}