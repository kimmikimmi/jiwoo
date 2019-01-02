package com.jworkplace.demo.domain.docube

import java.util.*

data class DiaryUnit(
        var id:String,
        var title: String,// month, date
        var weather: String,
        var body: String,
        var tags: List<String>,
        var isDeleted: Boolean,
        val createdAt: Date,
        var updatedAt: Date,
        val userId: String
        )
