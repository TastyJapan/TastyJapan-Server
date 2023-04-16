package com.tastyjapan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TastyjapanApplication

fun main(args: Array<String>) {
    runApplication<TastyjapanApplication>(*args)
}
