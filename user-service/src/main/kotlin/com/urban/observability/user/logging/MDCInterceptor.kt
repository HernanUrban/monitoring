package com.urban.observability.user.logging

import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object MDCInterceptor : HandlerInterceptor, Logging {
    override fun preHandle(request: HttpServletRequest, rs: HttpServletResponse, h: Any): Boolean {
        try {
            MDC.put("userId", request.userPrincipal?.name ?: "no-authorization-header")
        } catch (e: Throwable) {
            logger.warn("Failed to inject JWT's userId into MDCx", e)
        }
        return true
    }

    override fun afterCompletion(re: HttpServletRequest, rs: HttpServletResponse, h: Any, e: Exception?) = MDC.clear()
}
