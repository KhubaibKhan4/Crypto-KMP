package org.company.app.domain.repository

import org.company.app.data.remote.CryptoClient
import org.company.app.data.repository.CryptoApi
import org.company.app.domain.model.categories.NewsCategoriesItem
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.model.news.NewsList

class Repository: CryptoApi {
    override suspend fun getLatestListing(): LatestListing {
        return CryptoClient.getLatestListing()
    }

    override suspend fun getAllNews(): NewsList {
        return CryptoClient.getAllNews()
    }

    override suspend fun getNewsCategories(): List<NewsCategoriesItem> {
        return CryptoClient.getNewsCategories()
    }
}