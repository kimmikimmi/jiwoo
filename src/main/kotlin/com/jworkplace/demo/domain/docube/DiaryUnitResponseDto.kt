package com.jworkplace.demo.domain.docube

import java.util.*

data class DiaryUnitResponseDto(val id: String, val title: String, val weather: String, val body: String, val tags: List<String>, val userId: String, val modifiedDate: Date)
