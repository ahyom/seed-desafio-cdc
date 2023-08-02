package com.ahyom.cdc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration::class])
class CdcApplication

fun main(args: Array<String>) {
    runApplication<CdcApplication>(*args)
}
