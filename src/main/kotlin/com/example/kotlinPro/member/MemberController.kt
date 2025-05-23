package com.example.kotlinPro.member

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/register")
    fun registerMember(@RequestBody memberReqDto: MemberReqDto) {

        memberService.registerMember(memberReqDto)
    }

    @PostMapping("/update")
    fun updateMember(@RequestBody memberUpdateDto: MemberUpdateDto) {

        memberService.updateMember(memberUpdateDto)
    }

    @DeleteMapping("/delete/{username}")
    fun deleteMember(@PathVariable("username") username: String) {

        memberService.deleteMember(username)

    }
}