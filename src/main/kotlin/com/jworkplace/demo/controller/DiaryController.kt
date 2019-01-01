package com.jworkplace.demo.controller

import com.jworkplace.demo.common.extention.getLogger
import com.jworkplace.demo.domain.docube.DiaryService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class DiaryController (@Autowired val diaryService: DiaryService){
    val log = getLogger()

    @PostMapping()
    fun createNewDocube(): DocubeResponse {
        diaryService.putNewDocument()

        return DocubeResponse(200, "success to insert")
    }
}