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
                log.warn { "게시물 등록 실패: 사용자 '${postReqDto.writer}' 를 찾을 수 없습니다." }
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        postRepository.save(postReqDto.toPostEntity(member))

    }

    fun editPost(postUpdateDto: PostUpdateDto) {

        val post = postRepository.findById(postUpdateDto.id)
            .orElseThrow {
                log.warn { "수정 요청한 게시글을 찾을 수 없습니다. id=${postUpdateDto.id}" }
                TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND)
            }

        post.editPost(
            title = postUpdateDto.title,
            content = postUpdateDto.content,
            mbti = postUpdateDto.mbti,
            place = postUpdateDto.place,
            viewCnt = postUpdateDto.viewCnt,
            people = postUpdateDto.people,
            status = postUpdateDto.status
        )

        postRepository.save(post)
    }

    fun deletePost(id: Long) {

        val post = postRepository.findById(id)
            .orElseThrow {
                log.warn { "삭제 요청한 게시글을 찾을 수 없습니다. id=${id}" }
                TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND)
            }

        postRepository.delete(post)
        log.info { "게시글 삭제 완료. id=${post.id}, title='${post.title}'" }
    }
}