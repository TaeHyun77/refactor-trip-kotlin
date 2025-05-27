package com.example.kotlinPro.comment

import com.example.kotlinPro.post.Post

data class CommentReqDto(
    var content: String,
    var writer: String?
) {
    fun toComment(post: Post): Comment {
        return Comment(
            content = content,
            writer = writer,
            post = post
        )
    }
}