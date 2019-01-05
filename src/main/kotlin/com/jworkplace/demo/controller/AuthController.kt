package com.jworkplace.demo.controller

import com.jworkplace.demo.common.extention.getLogger
import com.jworkplace.demo.controller.DiaryResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class AuthController {
    val log = getLogger()

    @PostMapping("/_system/connect")
    fun connect(@RequestBody idToken: String): DiaryResponse {
        log.info("new request has come! userId:$idToken")
       return DiaryResponse(200, "connecting process has approved")
    }

    @PostMapping("/_system/disconnect")
    fun disconnect(@RequestBody idToken: String):DiaryResponse {
        log.info("new request has gone! userId:$idToken")
        return DiaryResponse(200, "disconnecting process has approved")
    }
}