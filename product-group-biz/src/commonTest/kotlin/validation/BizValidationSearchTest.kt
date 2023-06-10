package com.crowdproj.marketplace.product.group.biz.validation

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupFilter
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.models.PrgrpWorkMode
import com.crowdproj.marketplace.product.group.repo.stubs.PrgrpRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = PrgrpCommand.SEARCH
    private val settings by lazy {
        PrgrpCorSettings(
            repoTest = PrgrpRepoStub(),
        )
    }

    private val processor by lazy { PrgrpProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = PrgrpContext(
            command = command,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.TEST,
            groupFilterRequest = PrgrpGroupFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(PrgrpState.FAILING, ctx.state)
    }
}

