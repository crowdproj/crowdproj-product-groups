package com.crowdproj.marketplace.product.group.repo.stubs

import com.crowdproj.marketplace.product.group.stubs.PrgrpStub
import repo.*

class PrgrpRepoStub() : IPrgrpRepository {
    override suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        return DbPrgrpResponse(
            data = PrgrpStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        return DbPrgrpResponse(
            data = PrgrpStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        return DbPrgrpResponse(
            data = PrgrpStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        return DbPrgrpResponse(
            data = PrgrpStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse {
        return DbPrgrpsResponse(
            data = PrgrpStub.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }
}