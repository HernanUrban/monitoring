package com.urban.observability.address.controller

import com.urban.observability.address.service.Address
import com.urban.observability.address.service.AddressService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/addresses")
class AddressController(val service: AddressService) {

    @GetMapping("/user/{userId}")
    fun getAddressesByUserId(@PathVariable("userId") userId: Long): List<Address> {
        return service.getAddressesByUserId(userId)
    }
}