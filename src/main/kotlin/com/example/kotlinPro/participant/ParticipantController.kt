package com.example.kotlinPro.participant

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RequestMapping("/participant")
@RestController
class ParticipantController(
    private val participantService: ParticipantService
) {

    @PostMapping("/participate/{postId}")
    fun participate(@PathVariable("postId") postId: Long, @RequestBody participantReqDto: ParticipantReqDto) {

        return participantService.participate(postId, participantReqDto)
    }

    @DeleteMapping("/cancel/{postId}/{username}")
    fun cancelParticipant(@PathVariable("postId") postId: Long, @PathVariable("username") username: String) {

        return participantService.cancelParticipant(postId, username)
    }
}