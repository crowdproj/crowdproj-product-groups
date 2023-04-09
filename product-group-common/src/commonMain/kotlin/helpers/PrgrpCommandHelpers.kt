package com.crowdproj.marketplace.product.group.common.helpers

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand

fun PrgrpContext.isUpdatableCommand() =
    this.command in listOf(PrgrpCommand.CREATE, PrgrpCommand.UPDATE, PrgrpCommand.DELETE)
