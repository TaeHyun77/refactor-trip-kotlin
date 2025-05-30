package com.example.kotlinPro.member

import com.example.kotlinPro.config.fileUploader
import com.example.kotlinPro.jwt.JwtUtil
import com.example.kotlinPro.message.MessageResDto
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

private val log = KotlinLogging.logger {}

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {

    // 사용자 등록 ( 회원가입 )
    @Transactional
    fun registerMember(memberReqDto: MemberReqDto, file: MultipartFile?) {
        val encodedPassword = passwordEncoder.encode(memberReqDto.password)

        val role: Role = if (memberReqDto.username == "admin0515") Role.ADMIN else Role.USER

        val member = memberReqDto.toMemberEntity(encodedPassword, role)

        if (file != null) {
            val imageUrl = fileUploader("profileImage", file)

            member.profileImage = imageUrl
        }

        memberRepository.save(member)
    }

    // 사용자 수정
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

    // 사용자 삭제
    @Transactional
    fun deleteMember(username: String) {

        val member = searchMember(username)

        memberRepository.delete(member)
    }

    fun memberList(): List<MemberResDto> {

        return memberRepository.findAll().map {member ->
            MemberResDto (
                id = member.id,
                username = member.username,
                role = member.role,
                name = member.name,
                email = member.email,
                gender = member.gender,
                age = member.age
            )
        }
    }

    // 로그인 된 사용자 정보 조회
    fun memberInfo(token: String): ResponseEntity<MemberResDto> {
        return try {
            val username = jwtUtil.getUsername(token)
            val role = jwtUtil.getRole(token)

            val member = searchMember(username)

            val dto = MemberResDto(
                id = member.id,
                username = username,
                role = role,
                name = member.name,
                email = member.email,
                gender = member.gender,
                age = member.age,
                selfIntro = member.selfIntro,
                profileImage = member.profileImage,
                createdAt = member.createdAt,
                modifiedAt = member.modifiedAt,
                followers = member.followers.map { it.follower.username },
                followings = member.followings.map {it.following.username},
                messageList = member.messageList.map{message ->
                    MessageResDto(
                        receiver = message.receiver,
                        sender = message.sender,
                        title = message.title,
                        content = message.content,
                        createdAt = message.createdAt,
                        modifiedAt = message.modifiedAt
                    )
                }
            )

            ResponseEntity.ok(dto)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    // 사용자 프로필 이미지 조회
    fun getProfileImage(username: String): ResponseEntity<ProfileImageDto> {

        val member = searchMember(username)

        val imagePath = member.profileImage  // ex) /profileImages/uuid_image.png

        return ResponseEntity.ok(ProfileImageDto(profileImage = imagePath))
    }

    // member 조회 메서드
    fun searchMember(username: String): Member {

        val member = memberRepository.findByUsername(username)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)

        return member
    }
}