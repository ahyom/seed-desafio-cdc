package com.ahyom.cdc.controller.advice

import com.ahyom.cdc.controller.AutorController
import com.ahyom.cdc.domain.exception.BadRequestException
import com.ahyom.cdc.domain.exception.NotFoundException
import com.ahyom.cdc.domain.request.ErrorRequest
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice(assignableTypes = [AutorController::class])
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ErrorRequest> {
        logger.error("Handling BadRequestException: ${exception.message}", exception)
        return ResponseEntity(
            ErrorRequest(
                HttpStatus.BAD_REQUEST.reasonPhrase,
                HttpStatus.BAD_REQUEST.value(),
                exception.message,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorRequest> {
        logger.error("Handling IllegalArgumentException: ${exception.message}", exception)
        return ResponseEntity(
            ErrorRequest(
                HttpStatus.BAD_REQUEST.reasonPhrase,
                HttpStatus.BAD_REQUEST.value(),
                exception.message,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<ErrorRequest> {
        logger.error("Handling NotFoundException: ${exception.message}", exception)
        return ResponseEntity(
            ErrorRequest(
                HttpStatus.NOT_FOUND.reasonPhrase,
                HttpStatus.NOT_FOUND.value(),
                exception.message,
            ),
            HttpStatus.NOT_FOUND,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyKindOfGenericException(exception: Exception): ResponseEntity<ErrorRequest> {
        logger.error("Handling Exception: ${exception.message}", exception)
        return ResponseEntity(
            ErrorRequest(
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.message,
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}
