package org.company.app.domain.model.crypto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("credit_count")
    val creditCount: Int = 0,
    @SerialName("elapsed")
    val elapsed: Int = 0,
    @SerialName("error_code")
    val errorCode: Int = 0,
    @SerialName("error_message")
    val errorMessage: Any? = null,
    @SerialName("notice")
    val notice: Any? = null,
    @SerialName("timestamp")
    val timestamp: String = "",
    @SerialName("total_count")
    val totalCount: Int = 0
)