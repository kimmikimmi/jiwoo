package com.jworkplace.demo.common.extention.elasticsearch

import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.support.replication.ReplicationResponse
import java.lang.Exception

@Slf4j
class DefaultActionListener<T: ReplicationResponse> : ActionListener<T> {
    private val log = getLogger()

    override fun onFailure(e: Exception?) {
       log.error("ES send fail", e)

    }

    override fun onResponse(response: T) {
        log.debug("ES send success. response : {}", response)
    }

}