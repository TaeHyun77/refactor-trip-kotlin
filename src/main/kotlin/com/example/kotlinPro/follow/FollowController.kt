package com.example.kotlinPro.follow

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/follow")
@RestController
class FollowController(
    private val followService: FollowService
) {

    @PostMapping("/follow/{selfUsername}/{otherUsername}")
    fun follow(@PathVariable("selfUsername") selfUsername: String,
               @PathVariable("otherUsername") otherUsername: String) {

        return followService.follow(selfUsername, otherUsername)
    }

    @DeleteMapping("/delete/{selfId}/{otherId}")
    fun deleteFollow(@PathVariable("selfId") selfId: Long,
                     @PathVariable("otherId") otherId: Long) {

        return followService.deleteFollow(selfId, otherId)
    }
}