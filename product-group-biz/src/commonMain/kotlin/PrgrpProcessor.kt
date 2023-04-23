package com.crowdproj.marketplace.product.group.biz

import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.marketplace.product.group.biz.groups.operation
import com.crowdproj.marketplace.product.group.biz.groups.stubs
import com.crowdproj.marketplace.product.group.biz.validation.*
import com.crowdproj.marketplace.product.group.biz.workers.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId

class PrgrpProcessor() {
    suspend fun exec(ctx: PrgrpContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Status initialization")

            operation("Create product group", PrgrpCommand.CREATE) {
                stubs("Stub processing") {
                    stubCreateSuccess("Simulation of successful processing")
                    stubValidationBadTitle("Simulation a title validation error")
                    stubValidationBadDescription("Simulation a description validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub not permit")
                }
            }

            validation {
                worker("Copying the fields to prgrpValidating") { prgrpValidating = groupRequest.deepCopy() }
                worker("Cleaning id") { prgrpValidating.id = PrgrpGroupId.NONE }
                worker("Cleaning name") { prgrpValidating.name = prgrpValidating.name.trim() }
                worker("Cleaning description") { prgrpValidating.description = prgrpValidating.description.trim() }

                validateTitleNotEmpty("Check title is not empty")
                validateTitleHasContent("Check symbols")
                validateDescriptionNotEmpty("Check description is not empty")
                validateDescriptionHasContent("Check symbols")

                finishPrgrpValidation("Completion of checks")
            }

            operation("Receive product group", PrgrpCommand.READ) {
                stubs("Stub processing") {
                    stubReadSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulation an id validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub does not permit")
                }

                validation {
                    worker("Copying the fields to prgrpValidating") { prgrpValidating = groupRequest.deepCopy() }
                    worker("Cleaning id") { prgrpValidating.id = PrgrpGroupId(prgrpValidating.id.asString().trim()) }

                    validateIdNotEmpty("Check id is not empty")
                    validateIdProperFormat("Check id format")

                    finishPrgrpValidation("Completion of checks")
                }
            }

            operation("Change product group", PrgrpCommand.UPDATE) {
                stubs("Stub processing") {
                    stubUpdateSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulation an id validation error")
                    stubValidationBadTitle("Simulation a title validation error")
                    stubValidationBadDescription("Simulation a description validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub not permit")
                }

                validation {
                    worker("Copying the fields to prgrpValidating") { prgrpValidating = groupRequest.deepCopy() }
                    worker("Cleaning id") { prgrpValidating.id = PrgrpGroupId(prgrpValidating.id.asString().trim()) }
                    worker("Cleaning name") { prgrpValidating.name = prgrpValidating.name.trim() }
                    worker("Cleaning description") { prgrpValidating.description = prgrpValidating.description.trim() }

                    validateIdNotEmpty("Check id is not empty")
                    validateIdProperFormat("Check id format")
                    validateTitleNotEmpty("Check title is not empty")
                    validateTitleHasContent("Check symbols")
                    validateDescriptionNotEmpty("Check description is not empty")
                    validateDescriptionHasContent("Check symbols")

                    finishPrgrpValidation("Completion of checks")
                }
            }

            operation("Delete product group", PrgrpCommand.DELETE) {
                stubs("Stub processing") {
                    stubDeleteSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulation an id validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub does not permit")
                }

                validation {
                    worker("Copying the fields to prgrpValidating") { prgrpValidating = groupRequest.deepCopy() }
                    worker("Cleaning id") { prgrpValidating.id = PrgrpGroupId(prgrpValidating.id.asString().trim()) }

                    validateIdNotEmpty("Check id is not empty")
                    validateIdProperFormat("Check id format")

                    finishPrgrpValidation("Completion of checks")
                }
            }

            operation("Search product group", PrgrpCommand.SEARCH) {
                stubs("Stub processing") {
                    stubSearchSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulation an id validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub does not permit")
                }

                validation {
                    worker("Copying the fields to prgrpValidating") { prgrpValidating = groupRequest.deepCopy() }

                    finishPrgrpFilterValidation("Completion of checks")
                }
            }
        }.build()
    }
}