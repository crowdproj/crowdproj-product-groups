package com.crowdproj.marketplace.product.group.app.ktor

import PrgrpCorSettings

data class PrgrpAppSettings(
    val corSettings: PrgrpCorSettings,
    val processor: String,
)
