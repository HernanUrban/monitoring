package com.urban.observability.address.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleInternalError(e: Exception?): String {
        logger.error("internal server error", e)
        return String.format("Internal Server Error (traceId: %s)", MDC.get("X-B3-TraceId"))
    }
}