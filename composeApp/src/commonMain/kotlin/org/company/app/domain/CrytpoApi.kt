package org.company.app.domain

import org.company.app.domain.model.crypto.LatestListing

interface CrytpoApi {
    suspend fun getLatestListing(): LatestListing
}