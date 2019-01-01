package com.jworkplace.demo.domain.docube

import com.google.gson.Gson
import com.jworkplace.demo.common.extention.elasticsearch.DefaultActionListener
import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
@Slf4j
class DiaryRepository(@Autowired val restClient: RestHighLevelClient) {
    val logger = getLogger()

    fun insert(diaryUnit: DiaryUnit) {
        val gson = Gson()
        val indexRequest = IndexRequest("diary", "unit")
        indexRequest.source(gson.toJson(diaryUnit), XContentType.JSON)

        restClient.indexAsync(indexRequest, RequestOptions.DEFAULT, DefaultActionListener())
    }
}