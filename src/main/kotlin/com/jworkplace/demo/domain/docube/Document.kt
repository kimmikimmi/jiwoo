package com.jworkplace.demo.domain.docube

import java.util.*

data class Document(
        val id:String,
        val title: String,// month, date
        val weather: String,
        val body: String,
        val tags: List<String>,
        val isDeleted: Boolean,
        val createdAt: Date,
        val updatedAt: Date,
        val userId: String

        )
