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

object CryptoClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }
        defaultRequest {
            headers {
                append("X-CMC_PRO_API_KEY", API_KEY)
            }
        }
    }
    suspend fun getLatestListing(): LatestListing {
        return client.get(BASE_URL + "cryptocurrency/listings/latest").body()
    }
    suspend fun getAllNews(): NewsList{
        return client.get(CRYPTO_URL + "v2/news/?lang=EN").body()
    }
    suspend fun getNewsCategories(): List<NewsCategoriesItem>{
        return client.get(CRYPTO_URL + "data/news/categories").body()

    }
}