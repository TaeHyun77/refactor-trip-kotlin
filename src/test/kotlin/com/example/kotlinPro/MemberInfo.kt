package com.example.kotlinPro

data class MemberInfo (
    val name: String,
    val age: Int,

) {

    init {
        require(age >= 10)
        require(name.length in 1..6)
    }
}