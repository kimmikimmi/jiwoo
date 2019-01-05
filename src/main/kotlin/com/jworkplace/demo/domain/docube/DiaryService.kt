package com.jworkplace.demo.domain.docube

import lombok.extern.slf4j.Slf4j
import org.apache.http.client.utils.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Slf4j
@Service
class DiaryService(@Autowired val diaryRepository: DiaryRepository) {

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

        val diaryUnitDto = DiaryUnitDto(diaryUnit.title, diaryUnit.weather, diaryUnit.body, diaryUnit.tags, diaryUnit.userId)

        return DiaryUnitResponseDto(diaryUnitDto, diaryUnit.updatedAt)
    }

    fun get(userId: String, from: String, to: String): List<DiaryUnitResponseDto> {
        val startDate = DateUtils.parseDate(from)
        val endDate = DateUtils.parseDate(to)

        return  diaryRepository.getByUserAndUpdatedAtInRange(userId, startDate, endDate)
                .filter { it -> !it.isDeleted }
                .map { it ->
                    val dto = DiaryUnitDto(it.title, it.weather, it.body, it.tags, it.userId)
                    DiaryUnitResponseDto(dto, it.updatedAt)
                }

    }

}