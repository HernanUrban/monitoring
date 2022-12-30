package com.urban.observability.user.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.urban.observability.user.service.Address
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors


@Component
class AddressClient(
    builder: WebClient.Builder,
    @Value("\${client.address.url}") private val addressUrl: String,
    clientConnector: ReactorClientHttpConnector,
    private val mapper: ObjectMapper
) {

    private val httpClient = builder.baseUrl(addressUrl).clientConnector(clientConnector)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) .build()

    fun getAddressForUserId(userId: UUID): List<Address> {
        val addressFlux = httpClient.get()
            .uri("/addresses/user/{userId}", userId)
            .retrieve()
            .bodyToFlux(Address::class.java)
        return addressFlux.collect(Collectors.toList())
            .share().block()!!
    }

    fun createAddress(address: AddressClientRequest): Address {
        val addressMono = httpClient.post()
            .uri("/addresses")
            .body(Mono.just(address), AddressClientRequest::class.java)
            .retrieve()
            .bodyToMono(Address::class.java)
        return addressMono.block()!!
    }

}

data class AddressClientRequest(
    val userId: UUID,
    val type: String,
    val street: String,
    val zipcode: String,
    val country: String,
    val city: String
)
