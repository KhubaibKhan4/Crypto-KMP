package org.company.app.domain.model.crypto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class USD(
    @SerialName("fully_diluted_market_cap")
    val fullyDilutedMarketCap: Double = 0.0,
    @SerialName("last_updated")
    val lastUpdated: String = "",
    @SerialName("market_cap")
    val marketCap: Double = 0.0,
    @SerialName("market_cap_dominance")
    val marketCapDominance: Double = 0.0,
    @SerialName("percent_change_1h")
    val percentChange1h: Double = 0.0,
    @SerialName("percent_change_24h")
    val percentChange24h: Double = 0.0,
    @SerialName("percent_change_30d")
    val percentChange30d: Double = 0.0,
    @SerialName("percent_change_60d")
    val percentChange60d: Double = 0.0,
    @SerialName("percent_change_7d")
    val percentChange7d: Double = 0.0,
    @SerialName("percent_change_90d")
    val percentChange90d: Double = 0.0,
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("tvl")
    val tvl: Double? = null,
    @SerialName("volume_24h")
    val volume24h: Double = 0.0,
    @SerialName("volume_change_24h")
    val volumeChange24h: Double = 0.0
)