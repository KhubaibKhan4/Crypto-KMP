package org.company.app.domain.model.news


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsList(
    @SerialName("Data")
    val `data`: List<Data> = listOf(),
    @SerialName("HasWarning")
    val hasWarning: Boolean = false,
    @SerialName("Message")
    val message: String = "",
    @SerialName("Promoted")
    val promoted: List<String> = listOf(),
    @SerialName("RateLimit")
    val rateLimit: RateLimit = RateLimit(),
    @SerialName("Type")
    val type: Int = 0
)