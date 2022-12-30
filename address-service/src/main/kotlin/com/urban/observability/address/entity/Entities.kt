package com.urban.observability.address.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ADDRESS")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var addressId: Long? = null,
    @Column(columnDefinition = "uuid")
    var userId: UUID,
    var type: String,
    var street: String,
    var zipcode: String,
    var country: String,
    var city: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
)