package repo

import com.crowdproj.marketplace.product.group.common.helpers.errorRepoConcurrency
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import models.PrgrpGroupLock
import com.crowdproj.marketplace.product.group.common.helpers.errorEmptyId as prgrpErrorEmptyId
import com.crowdproj.marketplace.product.group.common.helpers.errorNotFound as prgrpErrorNotFound

data class DbPrgrpResponse(
    override val data: PrgrpGroup?,
    override val isSuccess: Boolean,
    override val errors: List<PrgrpError> = emptyList()
): IDbResponse<PrgrpGroup> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbPrgrpResponse(null, true)

        fun success(result: PrgrpGroup) = DbPrgrpResponse(result, true)

        fun error(errors: List<PrgrpError>, data: PrgrpGroup? = null) = DbPrgrpResponse(data, false, errors)

        fun error(error: PrgrpError, data: PrgrpGroup? = null) = DbPrgrpResponse(data, false, listOf(error))

        val errorEmptyId = error(prgrpErrorEmptyId)

        val errorNotFound = error(prgrpErrorNotFound)

        fun errorConcurrent(lock: PrgrpGroupLock, prgrpGroup: PrgrpGroup?) = error(
            errorRepoConcurrency(lock, prgrpGroup?.lock?.let { PrgrpGroupLock(it.asString()) }),
            prgrpGroup
        )
    }
}