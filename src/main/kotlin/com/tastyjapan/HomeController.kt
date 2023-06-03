package com.tastyjapan

import lombok.RequiredArgsConstructor
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class HomeController {
    @GetMapping("/")
    fun home(): String {
        return "Hello World"
    }
}