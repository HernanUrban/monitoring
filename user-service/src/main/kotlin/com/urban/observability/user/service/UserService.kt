package com.urban.observability.user.service

import com.urban.observability.user.client.AddressClient
import com.urban.observability.user.logging.Logging
import org.springframework.stereotype.Service

@Service
class UserService(private val addressClient: AddressClient): Logging {

    fun getUserById(id: Long): User {
        logger.info("Getting info for user $id")
        if (id == 5L) throw RuntimeException("Invalid ID")
        if (id == 3L) Thread.sleep(7000)
        val address = addressClient.getAddress(id)
        return User(
            id,
            "John Doe",
            "john.doe@mail.com",
            address
        )
    }
}

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val addresses: List<Address>?
)

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
