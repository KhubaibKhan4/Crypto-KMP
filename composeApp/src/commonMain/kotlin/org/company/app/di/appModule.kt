package org.company.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.company.app.data.remote.CryptoClient
import org.company.app.domain.repository.Repository
import org.company.app.presentation.viewmodel.MainViewModel
import org.company.app.utils.Constant
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient {
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
                filter { filter-> filter.url.host.contains("coinmarketcap.com") }
                sanitizeHeader { header-> header == HttpHeaders.Authorization }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = Constant.TIME_OUT
                connectTimeoutMillis = Constant.TIME_OUT
                socketTimeoutMillis = Constant.TIME_OUT
            }
            defaultRequest {
                headers {
                    append("X-CMC_PRO_API_KEY", Constant.API_KEY)
                }
            }
        }
    }
    single { CryptoClient(get()) }
    single {
        Repository(get())
    }
    singleOf(::MainViewModel)
}