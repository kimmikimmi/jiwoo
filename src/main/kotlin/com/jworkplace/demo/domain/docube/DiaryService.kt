package com.jworkplace.demo.domain.docube

import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.apache.http.client.utils.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Slf4j
@Service
class DiaryService(@Autowired val diaryRepository: DiaryRepository) {

    private val log = getLogger()

    fun putNewDocument(diaryUnitDto: DiaryUnitDto) {
        val diaryUnit = DiaryUnit("",
                diaryUnitDto.title,
                diaryUnitDto.weather,
                diaryUnitDto.body,
                diaryUnitDto.tags,
                false,
                Date(),
                Date(),
                diaryUnitDto.userId)

        diaryRepository.insert(diaryUnit)
    }


    fun updateDocument(id: String, diaryUnitDto: DiaryUnitDto) {
        val diaryUnit = DiaryUnit("",
                diaryUnitDto.title,
                diaryUnitDto.weather,
                diaryUnitDto.body,
                diaryUnitDto.tags,
                false,
                Date(),
                Date(),
                diaryUnitDto.userId)

        diaryRepository.update(id, diaryUnit)
    }

    fun get(id: String): DiaryUnitResponseDto {
        val diaryUnit = diaryRepository.getDocumentById(id)

        return DiaryUnitResponseDto(diaryUnit.id, diaryUnit.title, diaryUnit.weather, diaryUnit.body, diaryUnit.tags, diaryUnit.userId, diaryUnit.updatedAt)
    }

    fun get(userId: String, from: String, to: String): List<DiaryUnitResponseDto> {
        val startDate = SimpleDateFormat("yyyyMMdd").parse(from)
        val endDate = SimpleDateFormat("yyyyMMdd").parse(to)

        log.info("startDate = $startDate, endDate = $endDate")
        return  diaryRepository.getByUserAndUpdatedAtInRange(userId, startDate, endDate)
                .filter { it -> !it.isDeleted }
                .map { it ->
                    log.info("in map, title=${it.title} userId=${it.userId}")
                    DiaryUnitResponseDto(it.id, it.title, it.weather, it.body, it.tags, it.userId, it.updatedAt)
                }

    }

    fun deleteDocument(id: String) {
        return diaryRepository.delete(id)
    }

}