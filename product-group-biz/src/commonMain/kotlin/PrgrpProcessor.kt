package com.crowdproj.marketplace.product.group.biz

import PrgrpCorSettings
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.marketplace.product.group.biz.general.initRepo
import com.crowdproj.marketplace.product.group.biz.general.prepareResult
import com.crowdproj.marketplace.product.group.biz.groups.operation
import com.crowdproj.marketplace.product.group.biz.groups.stubs
import com.crowdproj.marketplace.product.group.biz.repo.*
import com.crowdproj.marketplace.product.group.biz.validation.*
import com.crowdproj.marketplace.product.group.biz.workers.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

class PrgrpProcessor(private val settings: PrgrpCorSettings = PrgrpCorSettings()) {
    suspend fun exec(ctx: PrgrpContext) = BusinessChain.exec(ctx.apply { settings =  this@PrgrpProcessor.settings})

    companion object {
        private val BusinessChain = rootChain {
            initStatus("Status initialization")
            initRepo("Repository initialization")

            operation("Create product group", PrgrpCommand.CREATE) {
                stubs("Stub processing") {
                    stubCreateSuccess("Simulation of successful processing")
                    stubValidationBadTitle("Simulation a title validation error")
                    stubValidationBadDescription("Simulation a description validation error")
                    stubDbError("Simulation a db work error")
                    stubNoCase("Error: requested stub not permit")
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
                chain {
                    title = "Save logic"
                    repoPrepareCreate("Preparing an object for saving")
                    repoCreate("Creating product group to DB")
                }
                prepareResult("Preparing a response")
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

                chain {
                    title = "Read logic"
                    repoRead("Reading a product group from DB")
                    worker {
                        title = "Preparing a response for Read"
                        on { state == PrgrpState.RUNNING }
                        handle { prgrpRepoDone = prgrpRepoRead }
                    }
                }
                prepareResult("Preparing a response")
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
                chain {
                    title = "Update logic"
                    repoRead("Read a product group from DB")
                    repoPrepareUpdate("Preparing an object for update")
                    repoUpdate("Updating a product group in DB")
                }
                prepareResult("Preparing a response")
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
                chain {
                    title = "Delete logic"
                    repoRead("Read a product group from DB")
                    repoPrepareDelete("Preparing an object for delete")
                    repoDelete("Delete a product group in DB")
                }
                prepareResult("Preparing a response")
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

                chain {
                    title = "Search logic"

                    repoSearch("Search a product group by filter in DB")
                }
                prepareResult("Preparing a response")
            }
        }.build()
    }
}