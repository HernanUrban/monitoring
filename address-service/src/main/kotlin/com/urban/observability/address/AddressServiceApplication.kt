package com.urban.observability.address

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AddressServiceApplication

fun main(args: Array<String>) {
	runApplication<AddressServiceApplication>(*args)
}
