package com.jworkplace.demo.auth

import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Slf4j
class RequestTokenAuthInterceptor(@Autowired val firebaseAuthManager: FirebaseAuthManager) : HandlerInterceptor {
    private val log = getLogger()

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true
        //개발 환경에서 테스트할때 사용하려고 우선 만들어 둠, 추후에 환경을 분리하여 적용해 보자.
//        if (request.getHeader("env") != null && request.getHeader("env").contains("develop")) {
//            return true
//        }
        val token = request.getHeader("token")
        try {
            val uuid = firebaseAuthManager.verifyToken(token)
            log.info("token=$token , uuid=$uuid")
        } catch (e: java.lang.Exception) {
            log.error("token: $token is not valid", e)
            return false
        }
        return true
    }
}