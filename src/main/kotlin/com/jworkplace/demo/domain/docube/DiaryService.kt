package com.jworkplace.demo.domain.docube

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Slf4j
@Service
class DiaryService(@Autowired val diaryRepository: DiaryRepository) {

    fun putNewDocument(diaryUnitDto: DiaryUnitDto): Int {
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
        return 1
    }


}