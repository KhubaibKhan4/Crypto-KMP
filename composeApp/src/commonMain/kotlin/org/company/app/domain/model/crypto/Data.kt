package org.company.app.domain.model.crypto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("circulating_supply")
    val circulatingSupply: Double = 0.0,
    @SerialName("cmc_rank")
    val cmcRank: Int = 0,
    @SerialName("date_added")
    val dateAdded: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("infinite_supply")
    val infiniteSupply: Boolean = false,
    @SerialName("last_updated")
    val lastUpdated: String = "",
    @SerialName("max_supply")
    val maxSupply: Double? = null,
    @SerialName("name")
    val name: String = "",
    @SerialName("num_market_pairs")
    val numMarketPairs: Int = 0,
    @SerialName("platform")
    val platform: Platform? = null,
    @SerialName("quote")
    val quote: Quote = Quote(),
    @SerialName("self_reported_circulating_supply")
    val selfReportedCirculatingSupply: Double? = null,
    @SerialName("self_reported_market_cap")
    val selfReportedMarketCap: Double? = null,
    @SerialName("slug")
    val slug: String = "",
    @SerialName("symbol")
    val symbol: String = "",
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("total_supply")
    val totalSupply: Double = 0.0,
    @SerialName("tvl_ratio")
    val tvlRatio: Double? = null
)