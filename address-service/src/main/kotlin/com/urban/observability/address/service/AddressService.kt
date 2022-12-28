package com.urban.observability.address.service

import com.urban.observability.address.logging.Logging
import org.springframework.stereotype.Service

@Service
class AddressService: Logging {

    fun getAddressesByUserId(userId: Long): List<Address>{
        if (userId == 3L) throw Exception("Something went wrong")
        logger.debug("Address retrieved for user $userId")
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