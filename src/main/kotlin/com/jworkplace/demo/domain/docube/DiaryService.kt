package com.jworkplace.demo.domain.docube

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class DiaryService(@Autowired val diaryRepository: DiaryRepository) {
    fun putNewDocument():Int {
        return 1
    }


}