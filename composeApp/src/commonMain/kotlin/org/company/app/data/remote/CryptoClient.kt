package org.company.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.company.app.domain.model.categories.NewsCategoriesItem
import org.company.app.domain.model.crypto.LatestListing
import org.company.app.domain.model.news.NewsList
import org.company.app.utils.Constant.API_KEY
import org.company.app.utils.Constant.BASE_URL
import org.company.app.utils.Constant.CRYPTO_URL
import org.company.app.utils.Constant.TIME_OUT

class CryptoClient(private val client: HttpClient) {
    suspend fun getLatestListing(): LatestListing {
        return client.get(BASE_URL + "cryptocurrency/listings/latest").body()
    }
    suspend fun getAllNews(): NewsList{
        return client.get(CRYPTO_URL + "v2/news/?lang=EN").body()
    }
    suspend fun getNewsCategories(): List<NewsCategoriesItem>{
        return client.get( CRYPTO_URL+"news/categories").body()

    }
}