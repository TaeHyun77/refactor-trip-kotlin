package com.example.kotlinPro.post

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

    @PostMapping("/write", consumes = ["multipart/form-data"])
    fun writePost(
        @RequestPart("postData") postReqDto: PostReqDto,
        @RequestPart("file") file: MultipartFile? // 선택적
    ): ResponseEntity<Any> {
        postService.writePost(postReqDto, file)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/edit")
    fun editPost(@RequestBody postUpdateDto: PostUpdateDto) {

        postService.editPost(postUpdateDto)

    }

    @DeleteMapping("/delete/{id}")
    fun deletePost(@PathVariable("id") id: Long) {

        postService.deletePost(id)

    }

    @GetMapping("/list")
    fun postList(): List<PostResDto> {
        return postService.postList()
    }
}