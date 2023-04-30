package com.crowdproj.marketplace.product.group.app.ktor

import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext

private val processor = PrgrpProcessor()

suspend fun process(ctx: PrgrpContext) = processor.exec(ctx)