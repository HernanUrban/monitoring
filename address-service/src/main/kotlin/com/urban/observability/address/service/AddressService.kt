package com.urban.observability.address.service

import com.urban.observability.address.entity.Address
import com.urban.observability.address.logging.Logging
import com.urban.observability.address.repository.AddressRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AddressService(val repo: AddressRepository): Logging {

    fun getAddressesByUserId(userId: UUID): List<AddressDTO> {
        val response = repo.findAllByUserId(userId)?.map(Address::toDTO).orEmpty()
        logger.debug("Address retrieved for user $userId : $response")
        return response
    }

    fun createAddressForUserId(addressRequest: AddressDTO): AddressDTO {
        logger.debug("Creating address for user ID ${addressRequest.userId}")
        val response = repo.save(addressRequest.toEntity()).toDTO()
        logger.debug("Address created $response")
        return response
    }
}

data class AddressDTO(
    var id: Long? = null,
    var userId: UUID,
    val type: AddressType,
    val street: String,
    val zipcode: String,
    val country: String,
    val city: String
) {
    fun toEntity(): Address = Address(
        addressId = id,
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

fun Address.toDTO(): AddressDTO = AddressDTO (
    id = addressId,
    userId = userId,
    type = AddressType.valueOf(type),
    street = street,
    zipcode = zipcode,
    country = country,
    city = city
)