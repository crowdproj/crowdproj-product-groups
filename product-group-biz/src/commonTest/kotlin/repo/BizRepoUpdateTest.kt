package com.crowdproj.marketplace.product.group.biz.repo

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpResponse
import com.crowdproj.marketplace.product.group.repo.tests.PrgrpRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.PrgrpGroupLock
import models.PrgrpVisibility
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = PrgrpUserId("321")
    private val command = PrgrpCommand.UPDATE
    private val initAd = PrgrpGroup(
        id = PrgrpGroupId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        visibility = PrgrpVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy {
        PrgrpRepositoryMock(
        invokeReadPrgrp = {
            DbPrgrpResponse(
                isSuccess = true,
                data = initAd,
            )
        },
        invokeUpdatePrgrp = {
            DbPrgrpResponse(
                isSuccess = true,
                data = PrgrpGroup(
                    id = PrgrpGroupId("123"),
                    name = "xyz",
                    description = "xyz",
                    visibility = PrgrpVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    )
    }
    private val settings by lazy {
        PrgrpCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { PrgrpProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val prgrpToUpdate = PrgrpGroup(
            id = PrgrpGroupId("123"),
            name = "xyz",
            description = "xyz",
            visibility = PrgrpVisibility.VISIBLE_TO_GROUP,
            lock = PrgrpGroupLock("123-234-abc-ABC"),
        )
        val ctx = PrgrpContext(
            command = command,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.TEST,
            groupRequest = prgrpToUpdate,
        )
        processor.exec(ctx)
        assertEquals(PrgrpState.FINISHING, ctx.state)
        assertEquals(prgrpToUpdate.id, ctx.groupResponse.id)
        assertEquals(prgrpToUpdate.name, ctx.groupResponse.name)
        assertEquals(prgrpToUpdate.description, ctx.groupResponse.description)
        assertEquals(prgrpToUpdate.visibility, ctx.groupResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
