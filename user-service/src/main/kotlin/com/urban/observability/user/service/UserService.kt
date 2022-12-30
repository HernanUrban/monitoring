package com.urban.observability.user.service

import com.urban.observability.user.client.AddressClient
import com.urban.observability.user.client.AddressClientRequest
import com.urban.observability.user.entity.User
import com.urban.observability.user.error.UserNotFoundException
import com.urban.observability.user.logging.Logging
import com.urban.observability.user.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.zip
import java.util.UUID

@Service
class UserService(
    private val repo: UserRepository,
    private val addressClient: AddressClient): Logging {

    fun getUserById(id: UUID): UserDTO {
        logger.debug("Getting info for user $id")
        val user = repo.findById(id).orElseThrow{
            UserNotFoundException(id.toString())
        }
        logger.debug("Getting addresses for user $id")
        val address = addressClient.getAddressForUserId(user.userId!!)
        val response = user.toUserDTO(address)
        logger.debug("User retrieved $response")
        return response
    }

    fun createUser(userDTO: UserDTO): UserDTO {
        logger.debug("Saving user for request: $userDTO")
        val user = repo.save(userDTO.toUserEntity())
        logger.debug("User created $user")
        logger.debug("Creating addresses for userId: ${user.userId}")
        val addresses = userDTO.addresses?.map{
            a -> addressClient.createAddress(a.toAddressClientRequest(user.userId!!))
        }.orEmpty()
        logger.debug("Address created for userId: ${user.userId} as follows $addresses")

        return user.toUserDTO(addresses)
    }
}

data class UserDTO(
    val id: UUID? = null,
    val name: String,
    val email: String,
    val addresses: List<Address>? = null
) {
    fun toUserEntity(): User = User(
        userId = id,
        name = name,
        email = email
    )
}

data class Address(
    val type: AddressType,
    val street: String,
    val zipcode: String,
    val country: String,
    val city: String
) {
    fun toAddressClientRequest(userId: UUID): AddressClientRequest = AddressClientRequest(
        userId = userId,
        type = type.name,
        street = street,
        zipcode = zipcode,
        country = country,
        city = city
    )
}

enum class AddressType {
    HOME,
    BUSINESS,
    MAILING
}

fun User.toUserDTO(addressList: List<Address>): UserDTO = UserDTO(
    id = userId,
    name = name,
    email = email,
    addresses = addressList
)