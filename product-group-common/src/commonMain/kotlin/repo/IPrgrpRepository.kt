package repo

interface IPrgrpRepository {
    suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse
    suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse
    suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse
    suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse
    suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse

    companion object {
        val NONE = object : IPrgrpRepository {
            override suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}