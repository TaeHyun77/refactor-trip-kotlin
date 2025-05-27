package com.example.kotlinPro.post

import com.example.kotlinPro.config.fileUploader
import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.member.MemberResDto
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

private val log = KotlinLogging.logger {}

@Service
class PostService (
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
){

    fun writePost(postReqDto: PostReqDto, file: MultipartFile?) {

        val member = memberRepository.findByName(postReqDto.writer)
            ?: run {
                log.warn { "게시물 등록 실패: 사용자 '${postReqDto.writer}' 를 찾을 수 없습니다." }
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        val post = postReqDto.toPostEntity(member)

        if (file != null) {
            val imagePath = fileUploader("postImage", file)
            post.postImage = imagePath
        }

        postRepository.save(post)
    }

    fun updatePost(postId: Long, postUpdateDto: PostUpdateDto, file: MultipartFile?) {

        val post = postRepository.findById(postId)
            .orElseThrow {
                log.warn { "게시글을 찾을 수 없습니다. id=${postId}" }
                TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND)
            }

        // file이 존재하면 앞에꺼, 존재하지 않으면 뒤에꺼
        val updatedImage = file?.let { fileUploader("postImage", it) } ?: post.postImage

        post.updatePost(
            title = postUpdateDto.title,
            content = postUpdateDto.content,
            mbti = postUpdateDto.mbti,
            place = postUpdateDto.place,
            postImage = updatedImage,
            travelStartDate = postUpdateDto.travelStartDate,
            travelEndDate = postUpdateDto.travelEndDate
        )

        postRepository.save(post)
    }

    fun removePost(postId: Long) {

        val post = postRepository.findById(postId)
            .orElseThrow {
                log.warn { "게시글을 찾을 수 없습니다. id=${postId}" }
                TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND)
            }

        postRepository.delete(post)
        log.info { "게시글 삭제 완료. id=${post.id}, title='${post.title}'" }
    }

    fun postList(): List<PostResDto> {
        val postList = postRepository.findAll()

        return postList.map { post ->
            PostResDto(
                id = post.id,
                title = post.title,
                content = post.content,
                writer = post.writer,
                mbti = post.mbti,
                place = post.place,
                people = post.people,
                viewCnt = post.viewCnt,
                postCategory = post.postCategory,
                status = post.status,
                postImage = post.postImage,
                createdAt = post.createdAt,
                modifiedAt = post.modifiedAt,
                travelStartDate = post.travelStartDate,
                travelEndDate = post.travelEndDate
            )
        }
    }

    fun postInfo(id: Long): PostResDto {

        val post = postRepository.findById(id)
            .orElseThrow {
                log.warn { "조회 게시글을 찾을 수 없습니다. id=${id}" }
                TripException(HttpStatus.NOT_FOUND, ErrorCode.POST_NOT_FOUND)
            }

        return PostResDto(
            id = post.id,
            title = post.title,
            content = post.content,
            writer = post.writer,
            mbti = post.mbti,
            place = post.place,
            people = post.people,
            viewCnt = post.viewCnt,
            postCategory = post.postCategory,
            status = post.status,
            postImage = post.postImage,
            createdAt = post.createdAt,
            modifiedAt = post.modifiedAt,
            travelStartDate = post.travelStartDate,
            travelEndDate = post.travelEndDate,
            member = MemberResDto(
                username = post.member.username,
                role = post.member.role,
                name = post.member.name,
                email = post.member.email,
                gender = post.member.gender,
                age = post.member.age,
                selfIntro = post.member.selfIntro,
                profileImage = post.member.profileImage
            )
        )
    }
}