package com.example.kotlinPro.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByUsername(username: String): Member?

    fun findByName(name: String): Member?
}