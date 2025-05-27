package com.example.kotlinPro.participant

import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository: JpaRepository<Participant, Long> {

    fun findByPostId(postId: Long): List<Participant>

    fun findByPostIdAndUsername(postId: Long, username: String): Participant
}