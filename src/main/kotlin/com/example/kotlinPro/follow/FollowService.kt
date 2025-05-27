package com.example.kotlinPro.follow

import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val memberRepository: MemberRepository,
    private val followRepository: FollowRepository
) {

    fun follow(selfUsername: String, otherUsername: String) {

        val self = memberRepository.findByUsername(selfUsername)
            ?:run {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        val other = memberRepository.findByUsername(otherUsername)
            ?:run {
                throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)
            }

        val follow = Follow(follower = self, following = other)

        followRepository.save(follow)
    }

    fun deleteFollow(selfId: Long, otherId: Long) {

        val follow: Follow = followRepository.findByFollowerIdAndFollowingId(selfId, otherId)

        followRepository.delete(follow)
    }
}