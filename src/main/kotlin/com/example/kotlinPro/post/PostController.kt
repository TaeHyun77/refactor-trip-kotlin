package com.example.kotlinPro.post

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @PostMapping("/edit")
    fun editPost(@RequestBody postUpdateDto: PostUpdateDto) {

        postService.editPost(postUpdateDto)

    }

    @DeleteMapping("/delete/{id}")
    fun deletePost(@PathVariable("id") id: Long) {

        postService.deletePost(id)

    }
}