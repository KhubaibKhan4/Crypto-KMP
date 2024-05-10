package org.company.app.domain.repository

import org.company.app.data.remote.CryptoClient
import org.company.app.data.repository.CryptoApi
import org.company.app.domain.model.crypto.LatestListing

class Repository: CryptoApi {
    override suspend fun getLatestListing(): LatestListing {
        return CryptoClient.getLatestListing()
    }
}