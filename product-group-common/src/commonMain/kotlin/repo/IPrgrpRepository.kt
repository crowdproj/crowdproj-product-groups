package repo

interface IPrgrpRepository {
    suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse
    suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse
    suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse
    suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse
    suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse
}