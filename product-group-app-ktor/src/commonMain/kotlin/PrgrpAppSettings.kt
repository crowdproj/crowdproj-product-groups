package com.crowdproj.marketplace.product.group.app.ktor

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor

data class PrgrpAppSettings(
    val corSettings: PrgrpCorSettings,
    val processor: PrgrpProcessor,
)
