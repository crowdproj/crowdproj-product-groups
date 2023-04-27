package com.crowdproj.marketplace.product.group.app.ktor

import com.crowdproj.marketplace.product.group.common.PrgrpCorSettings

data class PrgrpAppSettings(
    val corSettings: PrgrpCorSettings,
    val processor: String,
)
