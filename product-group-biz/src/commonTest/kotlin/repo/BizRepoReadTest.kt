package com.crowdproj.marketplace.product.group.biz.repo

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.repo.tests.PrgrpRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.PrgrpVisibility
import repo.DbPrgrpResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val userId = PrgrpUserId("321")
    private val command = PrgrpCommand.READ
    private val initPrgrp = PrgrpGroup(
        id = PrgrpGroupId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        visibility = PrgrpVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { PrgrpRepositoryMock(
        invokeReadPrgrp = {
            DbPrgrpResponse(
                isSuccess = true,
                data = initPrgrp,
            )
        }
    ) }
    private val settings by lazy {
        PrgrpCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { PrgrpProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = PrgrpContext(
            command = command,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.TEST,
            groupRequest = PrgrpGroup(
                id = PrgrpGroupId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpState.FINISHING, ctx.state)
        assertEquals(initPrgrp.id, ctx.groupResponse.id)
        assertEquals(initPrgrp.name, ctx.groupResponse.name)
        assertEquals(initPrgrp.description, ctx.groupResponse.description)
        assertEquals(initPrgrp.visibility, ctx.groupResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
