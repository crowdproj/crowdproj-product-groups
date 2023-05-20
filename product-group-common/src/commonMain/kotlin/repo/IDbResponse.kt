package repo

import com.crowdproj.marketplace.product.group.common.models.PrgrpError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<PrgrpError>
}