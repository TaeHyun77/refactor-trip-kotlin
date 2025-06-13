package com.example.kotlinPro

import com.example.kotlinPro.member.CheckUsername
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class ValidUsernameTest {

    @ParameterizedTest
    @ValueSource(strings = ["A1@abc", "T9%Test", "Z1^xyz", "@A1B2C"])
    fun `유효한 사용자명은 정상 생성`(input: String) {
        val result = CheckUsername(input)
        println("성공: $result")
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "A1abc",       // 특수문자 없음
        "a1@abc",      // 대문자 없음
        "ABC@def",     // 숫자 없음
        "A1\$abc",     // 허용되지 않은 특수문자
        "A1@ bc",      // 공백 포함
        "A1@-bc",      // 하이픈 포함
        "abc"          // 전부 없음
    ])
    fun `유효하지 않은 사용자명은 예외가 발생`(input: String) {
        assertThrows<IllegalArgumentException> {
            CheckUsername(input)
        }
    }

    @Test
    fun `이름 길이 초과 시 예외 발생`() {
        val name = "ABCDEFG" // 7자
        assertThrows<IllegalArgumentException> {
            successMemberInfo.copy(name = name)
        }
    }

    @Test
    fun `10세 미만은 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            MemberInfo(name = "lee", age = 9)
        }
    }

    private val successMemberInfo = MemberInfo(
        name = "park",
        age = 26,
    )
}