package com.crowdproj.marketplace.product.group.app.ktor.v1

import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.logging.common.ILogWrapper
import io.ktor.server.application.*


suspend fun ApplicationCall.createPrgrp(appSettings: PrgrpAppSettings, logger: ILogWrapper) =
    processV1<ProductGroupCreateRequest, ProductGroupCreateResponse>(appSettings, logger, "prgrp-create", PrgrpCommand.CREATE)

suspend fun ApplicationCall.readPrgrp(appSettings: PrgrpAppSettings, logger: ILogWrapper) =
    processV1<ProductGroupReadRequest, ProductGroupReadResponse>(appSettings, logger, "prgrp-read", PrgrpCommand.READ)

suspend fun ApplicationCall.updatePrgrp(appSettings: PrgrpAppSettings, logger: ILogWrapper) =
    processV1<ProductGroupUpdateRequest, ProductGroupUpdateResponse>(appSettings, logger, "prgrp-update", PrgrpCommand.UPDATE)

suspend fun ApplicationCall.deletePrgrp(appSettings: PrgrpAppSettings, logger: ILogWrapper) =
    processV1<ProductGroupDeleteRequest, ProductGroupDeleteResponse>(appSettings, logger, "prgrp-delete", PrgrpCommand.DELETE)

suspend fun ApplicationCall.searchPrgrp(appSettings: PrgrpAppSettings, logger: ILogWrapper) =
    processV1<ProductGroupSearchRequest, ProductGroupSearchResponse>(appSettings, logger, "prgrp-search", PrgrpCommand.SEARCH)