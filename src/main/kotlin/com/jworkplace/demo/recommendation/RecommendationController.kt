package com.jworkplace.demo.recommendation

import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern

@RestController
@Slf4j
class RecommendationController(@Autowired val recommendationComponent: RecommendationComponent) {

    val log = getLogger()

    @GetMapping(value = ["/recommendation/{word}"])
    fun getWordOf(@PathVariable word: String): String {

        return recommendationComponent.getWordOf(word)
    }


    @GetMapping("/recommendation/{word1}/{word2}")
    fun getWordOfs(@PathVariable word1: String, @PathVariable word2: String): Set<String> {

        val elem1 = recommendationComponent.getWordOf(word1)
        val elem2 = recommendationComponent.getWordOf(word2)
        val elem3 = recommendationComponent.getWordOf("$word1-$word2")
        val elem4 = recommendationComponent.getWordOf("$word2-$word1")
        val elem5 = recommendationComponent.getWordOf("$word1+$word2")
        return setOf(elem1, elem2, elem3, elem4, elem5)
    }
}