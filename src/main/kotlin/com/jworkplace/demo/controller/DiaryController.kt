package com.jworkplace.demo.controller

import com.google.api.client.util.Preconditions
import com.jworkplace.demo.common.extention.getLogger
import com.jworkplace.demo.domain.docube.DiaryService
import com.jworkplace.demo.domain.docube.DiaryUnitDto
import com.jworkplace.demo.domain.docube.DiaryUnitResponseDto
import lombok.extern.slf4j.Slf4j
import org.apache.http.client.utils.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @DeleteMapping("/diary/{id}")
    fun deleteDocument(@PathVariable id: String): DiaryResponse {
        log.info("deleteDocument. id=$id")

        diaryService.deleteDocument(id)

        return DiaryResponse(200, "success to delete")
    }

    @GetMapping("/diary/{id}")
    fun getDocument(@PathVariable id: String): DiaryUnitResponseDto {
        return diaryService.get(id)
    }

    @PostMapping("/diary/search")
    fun getDocuments(@RequestBody diaryCondition: DiaryCondition): List<DiaryUnitResponseDto> {
        Preconditions.checkNotNull(diaryCondition)

        val userId = diaryCondition.userId ?: ""
        val from = diaryCondition.from ?: DateUtils.formatDate(Date(), "yyyyMMdd")
        val to = diaryCondition.to ?: DateUtils.formatDate(Date(), "yyyyMMdd")

        val response  = diaryService.get(userId, from, to)
        log.info("userId = $userId, from=$from, to=$to, response=$response")

        return response
    }

     data class DiaryCondition(val userId: String?, val from: String?, val to: String?)
}