package com.crowdproj.marketplace.product.group.biz.validation

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.repo.stubs.PrgrpRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = PrgrpCommand.CREATE
    private val settings by lazy {
        PrgrpCorSettings(
            repoTest = PrgrpRepoStub(),
        )
    }

    private val processor by lazy { PrgrpProcessor(settings) }

    @Test
    fun correctTitle() = validationTitleCorrect(command, processor)
    @Test
    fun trimTitle() = validationTitleTrim(command, processor)
    @Test
    fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test
    fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test
    fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test
    fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test
    fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test
    fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

}

