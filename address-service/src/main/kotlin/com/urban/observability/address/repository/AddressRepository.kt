package com.urban.observability.address.repository

import com.urban.observability.address.entity.Address
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AddressRepository : CrudRepository<Address, Long> {
    fun findAllByUserId(userId: UUID): List<Address>?
}