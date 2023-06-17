package com.tastyjapan

import lombok.RequiredArgsConstructor
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class HomeController {
    @Value("\${management.metrics.tags.application}")
    private lateinit var application_name: String

    @GetMapping("/")
    fun home(): String {
        return application_name
    }
}