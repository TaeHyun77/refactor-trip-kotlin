package com.example.kotlinPro.post

import com.example.kotlinPro.member.Member
import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class PostService (
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository
){

    fun writePost(postReqDto: PostReqDto) {

        val member: Member = memberRepository.findByUsername(postReqDto.writer)
            ?: run {
                log.warn { "회원 정보를 찾을 수 없습니다. (게시물 등록 실패) username: ${postReqDto.writer}"}
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        postRepository.save(postReqDto.toPostEntity(member))

    }

    fun editPost(dto: PostUpdateDto) {

        val post = postRepository.findById(dto.id)
            .orElseThrow { TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND) }

        post.editPost(
            title = dto.title,
            content = dto.content,
            mbti = dto.mbti,
            place = dto.place,
            viewCnt = dto.viewCnt,
            people = dto.people,
            status = dto.status
        )

        postRepository.save(post)
    }
}