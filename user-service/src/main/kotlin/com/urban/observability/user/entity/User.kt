package com.urban.observability.user.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    var userId: UUID? = null,
    var name: String,
    var email: String
)