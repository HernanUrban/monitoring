package com.urban.observability.address.logging

import io.jaegertracing.internal.MDCScopeManager
import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer
import io.opentracing.util.GlobalTracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class MDCLoggingConfig : WebMvcConfigurer {
    override fun addInterceptors(r: InterceptorRegistry) {
        r.addInterceptor(JaegerInterceptor).addPathPatterns("/**")
        r.addInterceptor(MDCInterceptor).addPathPatterns("/**")
    }

    @Bean
    fun jaegerTraceBuilderCustomizer() = TracerBuilderCustomizer { x -> x.withScopeManager(MDCScopeManager.Builder().build()) }
}

object JaegerInterceptor : HandlerInterceptor, Logging {
    override fun preHandle(request: HttpServletRequest, rs: HttpServletResponse, h: Any): Boolean {
        val activeSpan = GlobalTracer.get().activeSpan()

        if (logger.isTraceEnabled()) {
            logger.trace(
                "Entrando JaegerInterceptor traceId=${activeSpan?.context()?.toTraceId()}, spanId=${activeSpan?.context()?.toSpanId()}, headers=${
                    request.headerNames.toList().map { "$it=${request.getHeader(it)}" }
                }"
            )
            logger.trace(
                "Entrando JaegerInterceptor baggage=${activeSpan?.context()?.baggageItems()}"
            )
        }

        val userId = activeSpan?.getBaggageItem("userid") // It isn't a typo, Jaeger flats camel cases

        logger.trace("userid=$userId")

        userId?.let {
            activeSpan.setTag("userId", it)
            logger.trace("userid seteado")
        }

        return true
    }
}