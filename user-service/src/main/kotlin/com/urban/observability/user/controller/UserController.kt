package com.urban.observability.user.controller


import com.urban.observability.user.service.UserDTO
import com.urban.observability.user.service.UserService
import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(val service: UserService) {


    @GetMapping("/{id}")
    @Timed(value = "getuser.time", description = "Time taken to return a User",
        percentiles = [0.5, 0.90]
    )
    fun getUser(@PathVariable("id") userId: UUID): UserDTO {
        return service.getUserById(userId)
    }

    @PostMapping
    @Timed(value = "createuser.time", description = "Time taken to return a new User",
        percentiles = [0.5, 0.90]
    )
    fun createUser(@RequestBody userDTO: UserDTO): UserDTO {
        return service.createUser(userDTO)
    }

}
