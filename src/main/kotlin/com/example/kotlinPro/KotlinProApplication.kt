package com.example.kotlinPro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDate
import kotlin.math.roundToInt

@EnableJpaAuditing
@SpringBootApplication
class KotlinProApplication

fun main(args: Array<String>) {
	runApplication<KotlinProApplication>(*args)
}

