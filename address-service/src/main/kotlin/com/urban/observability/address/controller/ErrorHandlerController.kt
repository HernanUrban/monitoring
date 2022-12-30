package com.urban.observability.address.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.urban.observability.address.exceptions.AddressException
import com.urban.observability.address.exceptions.GeneralServiceException
import com.urban.observability.address.exceptions.UnexpectedUserAddressException
import com.urban.observability.address.logging.Logging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class ErrorHandlerController : Logging {
    @ExceptionHandler
    fun handleException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> = (
            (ex as? AddressException) ?: UnexpectedUserAddressException(ex)
            ).also {
            if (it.status.is5xxServerError) logger.error("An unexpected exception has occurred", ex)
            if (it.status.is4xxClientError) logger.debug("A client exception has occurred", ex)
        }.run { ResponseEntity.status(status).body(this.toErrorResponse(request.requestURI)) }

}

fun GeneralServiceException.toErrorResponse(uri: String? = null): ErrorResponse {
    return ErrorResponse(
        code = code,
        message = message,
        timestamp = LocalDateTime.now(),
        cause = this.cause?.cause?.message ?: this.cause?.message,
        path = uri
    )
}
data class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: LocalDateTime,
    @JsonInclude(JsonInclude.Include.NON_NULL) val cause: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL) val path: String? = null
)
