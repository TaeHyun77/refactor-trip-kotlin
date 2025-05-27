package com.example.kotlinPro.comment

import com.example.kotlinPro.post.PostRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {

    // 댓글 작성
    fun writeComment(postId: Long, commentReqDto: CommentReqDto) {
        val post = postRepository.findById(postId).orElseThrow {
            TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
        }

        val comment = commentReqDto.toComment(post)

        commentRepository.save(comment)
    }

    // 댓글 수정
    fun updateComment(commentId: Long, commentReqDto: CommentReqDto) {

        var comment = commentRepository.findById(commentId)
            .orElseThrow {
                TripException(HttpStatus.BAD_REQUEST, ErrorCode.COMMENT_NOT_FOUND)
            }

        comment.updateComment(commentReqDto.content)

        commentRepository.save(comment)
    }

    // 댓글 삭제
    fun deleteComment(commentId: Long) {

        val comment = commentRepository.findById(commentId)
            .orElseThrow {
                TripException(HttpStatus.BAD_REQUEST, ErrorCode.COMMENT_NOT_FOUND)
            }

        commentRepository.delete(comment)
    }

    // 댓글 조회
    fun commentList(postId: Long): List<CommentResDto> {

        val post = postRepository.findById(postId)
            .orElseThrow {
                TripException(HttpStatus.BAD_REQUEST, ErrorCode.POST_NOT_FOUND)
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