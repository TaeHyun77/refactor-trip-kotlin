package com.example.kotlinPro.comment

import com.example.kotlinPro.post.PostRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {

    // 댓글 작성
    @Transactional
    fun writeComment(postId: Long, commentReqDto: CommentReqDto) {

        val post = postRepository.findById(postId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
            }

        val comment = commentReqDto.toComment(post)

        commentRepository.save(comment)
    }

    // 댓글 수정
    @Transactional
    fun updateComment(commentId: Long, commentReqDto: CommentReqDto) {

        val comment = commentRepository.findById(commentId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.COMMENT_NOT_FOUND)
            }

        comment.updateComment(commentReqDto.content)

        commentRepository.save(comment)
    }

    // 댓글 삭제
    @Transactional
    fun deleteComment(commentId: Long) {

        val comment = commentRepository.findById(commentId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.COMMENT_NOT_FOUND)
            }

        commentRepository.delete(comment)
    }

    // 댓글 조회
    fun commentList(postId: Long): List<CommentResDto> {

        val post = postRepository.findById(postId)
            .orElseThrow {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
            }

        return post.commentList.map {comment ->
            CommentResDto(
                id = comment.id,
                content = comment.content,
                writer = comment.writer,
                createdAt = comment.createdAt,
                modifiedAt = comment.modifiedAt
            )
        }
    }
}