package com.example.kotlinPro.follow

import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository: JpaRepository<Follow, Long> {

    fun findByFollowerIdAndFollowingId(selfId: Long, otherId: Long): Follow?

}