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
) {

    // 사용자 등록 ( 회원가입 )
    @PostMapping("/register", consumes = ["multipart/form-data"])
    fun registerMember(
        @RequestPart("user") memberReqDto: MemberReqDto,
        @RequestPart("file") file: MultipartFile?
    ): ResponseEntity<Any> {

        memberService.registerMember(memberReqDto, file)

        return ResponseEntity.ok().build()
    }

    // 사용자 수정
    @PostMapping("/update")
    fun updateMember(@RequestBody memberUpdateDto: MemberUpdateDto) {

        memberService.updateMember(memberUpdateDto)
    }

    // 사용자 삭제
    @DeleteMapping("/delete/{username}")
    fun deleteMember(@PathVariable("username") username: String) {

        memberService.deleteMember(username)

    }

    // 사용자 리스트 조회
    @GetMapping("/list")
    fun memberList(): List<MemberResDto> {

        return memberService.memberList();
    }

    // 사용자 정보 조회
    @GetMapping("/info")
    fun memberInfo(request: HttpServletRequest): ResponseEntity<MemberResDto> {

        val authorization = request.getHeader("Authorization")
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val token = authorization.substring(7)

        return memberService.memberInfo(token)
    }

    // 사용자 프로필 이미지 반환
    @GetMapping("/profileImage/{username}")
    fun profileImage(@PathVariable("username") username: String): ResponseEntity<ProfileImageDto> {

        return memberService.getProfileImage(username)
    }
}