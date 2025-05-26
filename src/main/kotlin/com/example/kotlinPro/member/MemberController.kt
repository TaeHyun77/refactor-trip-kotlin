package com.example.kotlinPro.member

import com.example.kotlinPro.jwt.JwtUtil
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

private val log = KotlinLogging.logger {}

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService,
    private val jwtUtil: JwtUtil,
    private val memberRepository: MemberRepository
) {

    @GetMapping("/info")
    fun memberInfo(request: HttpServletRequest): ResponseEntity<MemberResDto> {

        val authorization = request.getHeader("Authorization")
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val token = authorization.substring(7)

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
                profileImage = member.profileImage
            )

            ResponseEntity.ok(dto)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/register", consumes = ["multipart/form-data"])
    fun registerMember(
        @RequestPart("user") memberReqDto: MemberReqDto,
        @RequestPart("file") file: MultipartFile?
    ): ResponseEntity<Any> {

        memberService.registerMember(memberReqDto, file)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/update")
    fun updateMember(@RequestBody memberUpdateDto: MemberUpdateDto) {

        memberService.updateMember(memberUpdateDto)
    }

    @DeleteMapping("/delete/{username}")
    fun deleteMember(@PathVariable("username") username: String) {

        memberService.deleteMember(username)

    }

    @GetMapping("/profileImage/{username}")
    fun profileImage(@PathVariable("username") username: String): ResponseEntity<ProfileImageDto> {

        return memberService.getProfileImage(username)
    }
}