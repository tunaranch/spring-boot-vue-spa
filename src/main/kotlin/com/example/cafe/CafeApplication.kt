package com.example.cafe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CafeApplication

fun main(args: Array<String>) {
    runApplication<CafeApplication>(*args)
}
