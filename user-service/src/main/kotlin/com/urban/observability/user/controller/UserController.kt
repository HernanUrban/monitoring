package com.urban.observability.user.controller


import com.urban.observability.user.service.Address
import com.urban.observability.user.service.AddressType
import com.urban.observability.user.service.User
import com.urban.observability.user.service.UserService
import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val service: UserService) {


    @GetMapping("/{id}")
    @Timed(value = "getuser.time", description = "Time taken to return a User",
        percentiles = [0.5, 0.90]
    )
    fun getUser(@PathVariable("id") userId: Long): UserDTO {
        return service.getUserById(userId).toUserDTO()
    }

}

data class UserDTO(
    val id: Long,
    val name: String,
    val email: String,
    val address: List<AddressDTO>?
)

data class AddressDTO(
    val type: AddressTypeDTO,
    val street: String,
    val zipcode: String,
    val country: String,
    val city: String
)

enum class AddressTypeDTO {
    HOME,
    BUSINESS,
    MAILING
}

fun User.toUserDTO(): UserDTO{
    return UserDTO(
        id,
        name,
        email,
        addresses?.map(Address::toAddressDTO)
    )
}

fun Address.toAddressDTO(): AddressDTO {
    return AddressDTO(
        type.toAddressTypeDTO(),
        street,
        zipcode,
        country,
        city
    )
}

fun AddressType.toAddressTypeDTO(): AddressTypeDTO {
    return AddressTypeDTO.valueOf(this.toString())
}