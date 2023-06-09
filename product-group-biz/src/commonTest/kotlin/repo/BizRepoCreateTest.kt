package com.crowdproj.marketplace.product.group.biz.repo

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpResponse
import com.crowdproj.marketplace.product.group.repo.tests.PrgrpRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.PrgrpVisibility
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = PrgrpUserId("321")
    private val command = PrgrpCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = PrgrpRepositoryMock(
        invokeCreatePrgrp = {
            DbPrgrpResponse(
                isSuccess = true,
                data = PrgrpGroup(
                    id = PrgrpGroupId(uuid),
                    name = it.prGroup.name,
                    description = it.prGroup.description,
                    ownerId = userId,
                    visibility = it.prGroup.visibility,
                )
            )
        }
    )
    private val settings = PrgrpCorSettings(
        repoTest = repo
    )
    private val processor = PrgrpProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = PrgrpContext(
            command = command,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.TEST,
            groupRequest = PrgrpGroup(
                name = "abc",
                description = "abc",
                visibility = PrgrpVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpState.FINISHING, ctx.state)
        assertNotEquals(PrgrpGroupId.NONE, ctx.groupResponse.id)
        assertEquals("abc", ctx.groupResponse.name)
        assertEquals("abc", ctx.groupResponse.description)
        assertEquals(PrgrpVisibility.VISIBLE_PUBLIC, ctx.groupResponse.visibility)
    }
}
