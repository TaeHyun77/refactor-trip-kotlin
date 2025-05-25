package com.example.kotlinPro.jwt

import com.example.kotlinPro.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val member: Member
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(
            GrantedAuthority { "ROLE_${member.role.name}" }
        )
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.username
    }

    fun getName(): String {
        return member.name
    }

    fun getEmail(): String {
        return member.email
    }

    fun getGender(): String {
        return member.gender
    }

    fun getAge(): String {
        return member.age
    }
}