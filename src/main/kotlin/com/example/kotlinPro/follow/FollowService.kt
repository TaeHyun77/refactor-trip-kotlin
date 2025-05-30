package com.example.kotlinPro.follow

import com.example.kotlinPro.member.MemberRepository
import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowService(
    private val memberRepository: MemberRepository,
    private val followRepository: FollowRepository
) {

    // 팔로우 요청
    @Transactional
    fun follow(selfUsername: String, otherUsername: String) {

        val self = memberRepository.findByUsername(selfUsername)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)


        val other = memberRepository.findByUsername(otherUsername)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.MEMBER_NOT_FOUND)

        val follow = Follow(follower = self, following = other)

        followRepository.save(follow)
    }

    // 팔로우 취소
    @Transactional
    fun deleteFollow(selfId: Long, otherId: Long) {

        val follow = followRepository.findByFollowerIdAndFollowingId(selfId, otherId)
            ?: throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.FOLLOW_NOT_FOUND)

        followRepository.delete(follow)
    }
}

/* 만약 follow(1번, 2번)을 하게 된다면

연관관계에 따라 1번.following 리스트에 Follow(1번, 2번)이 들어가게 되고
, 2번.follower 리스트에 Follow(1번, 2번)이 들어가게 됨

만약 1번이 팔로잉한 목록이 궁금할 경우 1번의 following에 Follow(follower=1번, following=2번)이 들어가 있으니까
2번 사용자를 팔로잉했구나를 알 수 있음
*/