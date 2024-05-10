package org.company.app.domain.model.crypto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    @SerialName("USD")
    val uSD: USD = USD()
)