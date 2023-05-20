package repo

import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup

data class DbPrgrpsResponse (
    override val data: List<PrgrpGroup>?,
    override val isSuccess: Boolean,
    override val errors: List<PrgrpError> = emptyList(),
): IDbResponse<List<PrgrpGroup>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbPrgrpsResponse(emptyList(), true)
        fun success(result: List<PrgrpGroup>) = DbPrgrpsResponse(result, true)
        fun error(errors: List<PrgrpError>) = DbPrgrpsResponse(null, false, errors)
        fun error(error: PrgrpError) = DbPrgrpsResponse(null, false, listOf(error))
    }

}