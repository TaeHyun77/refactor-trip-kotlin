package com.example.kotlinPro.post

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/post")
@RestController
class PostController (
    private val postService: PostService
){

    @PostMapping("/write")
    fun writePost(@RequestBody postReqDto: PostReqDto) {

        postService.writePost(postReqDto)

    }
}