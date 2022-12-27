package com.urban.observability.address.service

import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class AddressService {

    fun getAddressesByUserId(userId: Long): List<Address>{
        if (userId == 3L) throw RuntimeException("Something went wrong")
        return listOf(
            Address(
                AddressType.HOME,
                "Fake St. 123",
                "12345",
                "USA",
                "Los Angeles"
            )
        )
    }
}

data class Address(
    val type: AddressType,
    val street: String,
    val zipcode: String,
    val country: String,
    val city: String
)

enum class AddressType {
    HOME,
    BUSINESS,
    MAILING
}