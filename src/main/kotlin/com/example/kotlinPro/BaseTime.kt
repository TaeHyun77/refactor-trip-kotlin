package com.example.kotlinPro

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTime {

    // JPA가 객체를 만들 때 값을 넣어주지만, 컴파일 타임에는 그것을 알 수 없기 때문에 null로 초기화 시켜줌
    // lateinit var createdAt: LocalDateTime => 이렇게도 가능
    // val로 선언하면 JPA가 나중에 값을 넣어주지 못하므로 에러가 발생함
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null
}