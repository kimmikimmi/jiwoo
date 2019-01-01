package com.jworkplace.demo.common.extention.elasticsearch

import org.apache.http.HttpHost
import org.springframework.stereotype.Component


@Component
class ElasticSearchProperties {
    private val host: String = "localhost:9200"
    val hosts = listOf(host)

    fun hosts(): Array<HttpHost> = hosts.map { it -> HttpHost.create(it) }.toTypedArray()


}