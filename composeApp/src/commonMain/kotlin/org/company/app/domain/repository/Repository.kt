package org.company.app.domain.repository

import org.company.app.data.remote.CryptoClient
import org.company.app.data.repository.CryptoApi
import org.company.app.domain.model.categories.NewsCategoriesItem
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.model.news.NewsList

class Repository(
    private val cryptoClient: CryptoClient
): CryptoApi {
    override suspend fun getLatestListing(): LatestListing {
        return cryptoClient.getLatestListing()
    }

    override suspend fun getAllNews(): NewsList {
        return cryptoClient.getAllNews()
    }

    override suspend fun getNewsCategories(): List<NewsCategoriesItem> {
        return cryptoClient.getNewsCategories()
    }
}