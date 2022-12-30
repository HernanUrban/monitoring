package com.urban.observability.user.repository

import com.urban.observability.user.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, UUID> {
}