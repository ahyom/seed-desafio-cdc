package com.ahyom.cdc.domain.request

data class ErrorRequest(val error: String, val status: Int, val cause: String?)
