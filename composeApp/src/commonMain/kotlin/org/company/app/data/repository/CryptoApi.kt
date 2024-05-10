package org.company.app.data.repository

import org.company.app.domain.model.crypto.LatestListing

interface CryptoApi {
    suspend fun getLatestListing(): LatestListing
}