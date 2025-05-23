package com.example.kotlinPro.member

import com.example.kotlinPro.BaseTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@Entity
class Member (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var username: String,

    @Column
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column
    var role: Role,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column
    val category: String,

    @Column
    var gender: String,

    @Column
    var age: String,

    @Column
    var selfIntro: String,

): BaseTime() {

    fun updateMember(name: String, email: String, gender: String, age: String, selfIntro: String) {
        this.name = name
        this.email = email
        this.gender = gender
        this.age = age
        this.selfIntro = selfIntro
    }

}