package com.jworkplace.demo.controller

import com.google.api.client.util.Preconditions
import com.jworkplace.demo.common.extention.getLogger
import com.jworkplace.demo.domain.docube.DiaryService
import com.jworkplace.demo.domain.docube.DiaryUnitDto
import com.jworkplace.demo.domain.docube.DiaryUnitResponseDto
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
class DiaryController(@Autowired val diaryService: DiaryService) {
    val log = getLogger()

    @PostMapping("/diary")
    fun createDocument(@RequestBody diaryUnitDto: DiaryUnitDto): DiaryResponse {
        Preconditions.checkNotNull(diaryUnitDto)

        log.info("create new diary diaryUnitDto=$diaryUnitDto")
        diaryService.putNewDocument(diaryUnitDto)

        return DiaryResponse(200, "success to insert")
    }

    @PostMapping("/diary/{id}")
    fun updateDocument(@PathVariable id: String, @RequestBody diaryUnitDto: DiaryUnitDto): DiaryResponse {
        Preconditions.checkNotNull(diaryUnitDto)

        log.info("updateDocument. diaryUnitDto=$diaryUnitDto, documentId=$id")
        diaryService.updateDocument(id, diaryUnitDto)

        return DiaryResponse(200, "success to update")
    }

    @GetMapping("/diary/{id}")
    fun getDocument(@PathVariable id: String): DiaryUnitResponseDto {
        return diaryService.get(id)
    }

    @GetMapping("/diary/{userId}")
    fun getDocuments(@PathVariable userId: String, @RequestParam from: String, @RequestParam to: String): List<DiaryUnitResponseDto> {
        return diaryService.get(userId, from, to)
    }
}