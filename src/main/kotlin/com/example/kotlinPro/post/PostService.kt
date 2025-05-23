package com.example.kotlinPro.post

import org.springframework.stereotype.Service

@Service
class PostService (
    private val postRepository: PostRepository
){

    fun writePost(postReqDto: PostReqDto) {

        postRepository.save(postReqDto.toPostEntity())

    }

}