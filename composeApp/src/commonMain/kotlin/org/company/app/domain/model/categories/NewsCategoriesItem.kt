package org.company.app.domain.model.categories


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsCategoriesItem(
    @SerialName("categoryName")
    val categoryName: String = "",
    @SerialName("excludedPhrases")
    val excludedPhrases: List<String>? = null,
    @SerialName("includedPhrases")
    val includedPhrases: List<String>? = null,
    @SerialName("wordsAssociatedWithCategory")
    val wordsAssociatedWithCategory: List<String>? = null
)