package com.urban.observability.user.service

import com.urban.observability.user.client.AddressClient
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(private val addressClient: AddressClient) {
    companion object {
        val LOG = Logger.getLogger(UserService::class.java.name)
    }

    fun getUserById(id: Long): User {
        LOG.info("Getting info for user %id")
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
