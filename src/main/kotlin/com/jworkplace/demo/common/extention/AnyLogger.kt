package com.jworkplace.demo.common.extention

import org.slf4j.LoggerFactory

fun <R: Any> R.getLogger() = LoggerFactory.getLogger(this::class.java)!!