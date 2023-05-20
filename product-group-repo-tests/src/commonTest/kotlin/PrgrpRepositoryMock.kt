package com.crowdproj.marketplace.product.group.repo.tests

import repo.*

class PrgrpRepositoryMock(
    private val invokeCreatePrgrp: (DbPrgrpRequest) -> DbPrgrpResponse = { DbPrgrpResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadPrgrp: (DbPrgrpIdRequest) -> DbPrgrpResponse = { DbPrgrpResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdatePrgrp: (DbPrgrpRequest) -> DbPrgrpResponse = { DbPrgrpResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeletePrgrp: (DbPrgrpIdRequest) -> DbPrgrpResponse = { DbPrgrpResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchPrgrp: (DbPrgrpFilterRequest) -> DbPrgrpsResponse = { DbPrgrpsResponse.MOCK_SUCCESS_EMPTY },
): IPrgrpRepository {
    override suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        return invokeCreatePrgrp(rq)
    }

    override suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        return invokeReadPrgrp(rq)
    }

    override suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        return invokeUpdatePrgrp(rq)
    }

    override suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        return invokeDeletePrgrp(rq)
    }

    override suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse {
        return invokeSearchPrgrp(rq)
    }
}