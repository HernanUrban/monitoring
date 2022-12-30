package com.urban.observability.address.controller

import com.urban.observability.address.service.AddressDTO
import com.urban.observability.address.service.AddressService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/addresses")
class AddressController(val service: AddressService) {

    @GetMapping("/user/{userId}")
    fun getAddressesByUserId(@PathVariable("userId") userId: UUID): ResponseEntity<List<AddressDTO>> {
        return ResponseEntity(service.getAddressesByUserId(userId), HttpStatus.OK)
    }

    @PostMapping
    fun createAddress(@RequestBody address: AddressDTO): ResponseEntity<AddressDTO> {
        return ResponseEntity(service.createAddressForUserId(address), HttpStatus.CREATED)
    }
}