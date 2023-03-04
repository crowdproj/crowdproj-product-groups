package com.crowdproj.marketplace.product.group.mapper.exceptions

import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand


class UnknownPrgrpCommand(command: PrgrpCommand) : Throwable("Wrong command $command at mapping toTransport stage")
