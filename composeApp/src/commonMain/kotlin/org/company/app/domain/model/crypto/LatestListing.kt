package org.company.app.domain.model.crypto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestListing(
    @SerialName("data")
    val `data`: List<Data> = listOf(),
    @SerialName("status")
    val status: Status = Status()
)