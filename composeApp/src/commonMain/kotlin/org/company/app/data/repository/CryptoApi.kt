package org.company.app.data.repository

import org.company.app.domain.model.categories.NewsCategoriesItem
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.model.news.NewsList

interface CryptoApi {
    suspend fun getLatestListing(): LatestListing
    suspend fun getAllNews(): NewsList
    suspend fun getNewsCategories(): List<NewsCategoriesItem>
}