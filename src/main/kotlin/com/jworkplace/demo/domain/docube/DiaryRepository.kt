package com.jworkplace.demo.domain.docube

import com.google.gson.Gson
import com.jworkplace.demo.common.extention.elasticsearch.DefaultActionListener
import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Slf4j
class DiaryRepository(@Autowired val restClient: RestHighLevelClient) {
    private val indexName = "diary"
    private val type = "unit"
    private val log = getLogger()

    fun insert(diaryUnit: DiaryUnit) {
        val indexRequest = IndexRequest(indexName, type)
        indexRequest.source(Gson().toJson(diaryUnit), XContentType.JSON)

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

    fun getByUserAndUpdatedAtInRange(userId: String, startDate: Date?, endDate: Date?): List<DiaryUnit> {
        val searchRequest = SearchRequest(indexName)
        searchRequest.types(type)

        log.info("startDate=$startDate endDate=$endDate userId=$userId")
        val searchSourceBuilder = SearchSourceBuilder()
        searchSourceBuilder
                .query(
                        QueryBuilders.matchAllQuery()
//                        QueryBuilders.boolQuery()
//                                .filter(QueryBuilders.rangeQuery("updatedAt")
//                                        .gte(startDate)
//                                        .lt(endDate))
//                                .filter(QueryBuilders.termQuery("userId", userId))
                )
                .size(31)
                .sort("updatedAt.keyword")

        searchRequest.source(searchSourceBuilder)

        return search(searchRequest)
    }


    fun search(searchRequest: SearchRequest): List<DiaryUnit> {
        val diaryUnits = mutableListOf<DiaryUnit>()
        val searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT)
        searchResponse.hits
                .forEach { it ->
                    val diaryUnit = Gson().fromJson(it.sourceAsString, DiaryUnit::class.java)
                    diaryUnit.id = it.id
                    diaryUnits.add(diaryUnit)
                }

        return diaryUnits
    }

}