package com.example.kotlinPro.comment

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/comment")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    // 댓글 작성
    @PostMapping("/write/{postId}")
    fun writeComment(@PathVariable("postId") postId: Long, @RequestBody commentReqDto: CommentReqDto) {

        return commentService.writeComment(postId, commentReqDto)
    }

    // 댓글 수정
    @PostMapping("/update/{commentId}")
    fun updateComment(@PathVariable("commentId") commentId: Long, @RequestBody commentReqDto: CommentReqDto) {

        return commentService.updateComment(commentId, commentReqDto)
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    fun deleteComment(@PathVariable("commentId") commentId: Long) {

        return commentService.deleteComment(commentId)
    }

    // 댓글 리스트 조회
    @GetMapping("/list/{postId}")
    fun commentList(@PathVariable("postId") postId: Long): List<CommentResDto>  {

        return commentService.commentList(postId)
    }
}