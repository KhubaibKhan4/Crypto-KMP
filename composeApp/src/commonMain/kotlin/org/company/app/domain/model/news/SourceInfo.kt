package org.company.app.domain.model.news


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceInfo(
    @SerialName("img")
    val img: String = "",
    @SerialName("lang")
    val lang: String = "",
    @SerialName("name")
    val name: String = ""
)