package com.jworkplace.demo.controller

import com.jworkplace.demo.common.extention.getLogger
import com.jworkplace.demo.domain.docube.DiaryService
import com.jworkplace.demo.domain.docube.DiaryUnitDto
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class DiaryController (@Autowired val diaryService: DiaryService){
    val log = getLogger()

    @PostMapping("/diary")
    fun createNewDocube(@RequestBody diaryUnitDto: DiaryUnitDto): DocubeResponse {

        diaryService.putNewDocument(diaryUnitDto)

        return DocubeResponse(200, "success to insert")
    }
}