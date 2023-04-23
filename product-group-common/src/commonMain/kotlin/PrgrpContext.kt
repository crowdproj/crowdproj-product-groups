package com.crowdproj.marketplace.product.group.common

import com.crowdproj.marketplace.product.group.common.models.*
import kotlinx.datetime.Instant
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs

data class PrgrpContext(
    var command: PrgrpCommand = PrgrpCommand.NONE,
    var state: PrgrpState = PrgrpState.NONE,
    val errors: MutableList<PrgrpError> = mutableListOf(),

    var workMode: PrgrpWorkMode = PrgrpWorkMode.PROD,
    var stubCase: PrgrpStubs = PrgrpStubs.NONE,

    var requestId: PrgrpRequestId = PrgrpRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var groupRequest: PrgrpGroup = PrgrpGroup(),
    var groupFilterRequest: PrgrpGroupFilter = PrgrpGroupFilter(),
    var groupResponse: PrgrpGroup = PrgrpGroup(),
    var groupsResponse: MutableList<PrgrpGroup> = mutableListOf(),

    var prgrpValidating: PrgrpGroup = PrgrpGroup(),
    var prgrpFilterValidating: PrgrpGroupFilter = PrgrpGroupFilter(),

    var prgrpValidated: PrgrpGroup = PrgrpGroup(),
    var prgrpFilterValidated: PrgrpGroupFilter = PrgrpGroupFilter(),

   )
