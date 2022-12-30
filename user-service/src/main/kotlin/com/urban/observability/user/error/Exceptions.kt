package com.urban.observability.user.error

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

abstract class GeneralServiceException(
    val code: String,
    override val message: String,
    @JsonIgnore val status: HttpStatus,
    @JsonIgnore override val cause: Throwable? = null
) : RuntimeException(message, cause)

sealed class UserException(
    code: String,
    message: String,
    status: HttpStatus = BAD_REQUEST,
    cause: Throwable? = null
) : GeneralServiceException(
    code,
    message,
    status,
    cause
)

class UserNotFoundException(
    id: String
) : UserException(
    "users.not.found.error",
    "No user found for $id",
    HttpStatus.NOT_FOUND
)

class InvalidUserAttributesException(
    params: Array<String>? = null
) : UserException(
    "users.inavlid.params.error",
    "Invalid or wrong params $params"
)


class UnexpectedUserUserException(ex: Throwable) : GeneralServiceException(
    code = "system.unexpected.error",
    message = "Something went really wrong with Address-Service",
    status = INTERNAL_SERVER_ERROR,
    cause = ex
)


class UserAccessDeniedException(
    message: String,
    ex: Throwable
) : UserException(
    code = "authentication.forbidden.error",
    message = message,
    status = FORBIDDEN,
    cause = ex
)

