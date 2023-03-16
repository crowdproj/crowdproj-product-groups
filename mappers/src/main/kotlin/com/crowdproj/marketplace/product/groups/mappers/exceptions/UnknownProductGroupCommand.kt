package com.crowdproj.marketplace.product.groups.mappers.exceptions

import com.crowdproj.marketplace.product.groups.common.models.ProductGroupCommand

class UnknownProductGroupCommand(command: ProductGroupCommand) : Throwable("Wrong command $command at mapping toTransport stage")