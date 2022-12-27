package com.urban.observability.user.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.urban.observability.user.service.Address
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
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

    fun getAddress(userId: Long): List<Address> {
        val addressFlux = httpClient.get()
            .uri("/addresses/user/{userId}", userId)
            .retrieve()
            .bodyToFlux(Address::class.java)
        return addressFlux.collect(Collectors.toList())
            .share().block()!!
    }

}

data class Addresses(
    val addressList: List<Address>
)