package com.jworkplace.demo.controller

import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class HelloController {
    private val log = getLogger()

    @GetMapping("/hello")
    fun  hello(): String {
        return "world!"
    }
}