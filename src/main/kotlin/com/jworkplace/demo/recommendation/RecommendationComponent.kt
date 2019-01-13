package com.jworkplace.demo.recommendation

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate



@Component
class RecommendationComponent {

    fun getWordOf(word: String): String {
        val uri = "http://w.elnn.kr/search/?query=$word"

        val restTemplate = RestTemplate()
        val html = restTemplate.getForObject(uri, String::class.java).toString()

        val splitedString = html.split("result")

        return splitedString[1].substring(2, splitedString[1].indexOf("/"))
    }
}