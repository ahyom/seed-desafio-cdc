package com.ahyom.cdc.controller.advice

import com.ahyom.cdc.controller.AutorController
import com.ahyom.cdc.domain.request.ErrorRequest
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice(assignableTypes = [AutorController::class])
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleAnyKindOfGenericException(exception: Exception): ResponseEntity<ErrorRequest> {
        logger.error("Handling Exception: ${exception.message}", exception)
        return ResponseEntity(
            ErrorRequest(
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                HttpStatus.INTERNAL_SERVER_ERROR.ordinal,
                exception.message,
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}
