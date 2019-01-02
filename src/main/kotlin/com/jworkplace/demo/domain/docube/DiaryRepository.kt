package com.jworkplace.demo.domain.docube

import com.google.gson.Gson
import com.jworkplace.demo.common.extention.elasticsearch.DefaultActionListener
import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
@Slf4j
class DiaryRepository(@Autowired val restClient: RestHighLevelClient) {
    private val indexName = "diary"
    private val type = "unit"
    private val log = getLogger()

    fun insert(diaryUnit: DiaryUnit) {
        val gson = Gson()
        val indexRequest = IndexRequest(indexName, type)
        indexRequest.source(gson.toJson(diaryUnit), XContentType.JSON)

        log.info(indexRequest.toString())

        restClient.indexAsync(indexRequest, RequestOptions.DEFAULT, DefaultActionListener())
    }

    fun getDocumentById(id: String): DiaryUnit {

        val getRequest = GetRequest(indexName, type, id)
        val getResponse = restClient.get(getRequest, RequestOptions.DEFAULT)

        return Gson().fromJson(getResponse.sourceAsString, DiaryUnit::class.java)
    }

    fun delete(id: String) {
        val updateRequest = UpdateRequest()
        updateRequest.index(indexName)
        updateRequest.type(type)
        updateRequest.id(id)

        updateRequest.doc(
                jsonBuilder()
                        .startObject()
                        .field("isDeleted", true)
                        .endObject()
        )

        restClient.updateAsync(updateRequest, RequestOptions.DEFAULT, DefaultActionListener())
    }

    fun update(id: String, diaryUnit: DiaryUnit) {
        delete(id)
        insert(diaryUnit)
    }



}