package com.example.kotlinPro.post

import org.springframework.http.MediaType
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

@RequestMapping("/post")
@RestController
class PostController (
    private val postService: PostService
){

    // 게시물 작성
    @PostMapping("/write", consumes = ["multipart/form-data"])
    fun writePost(
        @RequestPart("postData") postReqDto: PostReqDto,
        @RequestPart("file") file: MultipartFile? // 선택적
    ): ResponseEntity<Any> {
        postService.writePost(postReqDto, file)
        return ResponseEntity.ok().build()
    }

    // 게시물 수정
    @PostMapping("/{postId}", consumes = ["multipart/form-data"])
    fun updatePost(@PathVariable postId: Long, @RequestPart("postUpdateData") postUpdateDto: PostUpdateDto,
        @RequestPart("file") file: MultipartFile?
    ): ResponseEntity<Any> {
        postService.updatePost(postId, postUpdateDto, file)
        return ResponseEntity.ok().build()
    }

    // 게시물 삭제
    @DeleteMapping("/remove/{postId}")
    fun deletePost(@PathVariable("postId") postId: Long) {

        postService.removePost(postId)

    }

    // 모든 게시물 조회
    @GetMapping("/list")
    fun postList(): List<PostResDto> {
        return postService.postList()
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    fun postInfo(@PathVariable("id") id: Long): PostResDto {
        return postService.postInfo(id)
    }
}