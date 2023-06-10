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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = PrgrpUserId("321")
    private val command = PrgrpCommand.DELETE
    private val initPrgrp = PrgrpGroup(
        id = PrgrpGroupId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        visibility = PrgrpVisibility.VISIBLE_PUBLIC,
        lock = PrgrpGroupLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        PrgrpRepositoryMock(
            invokeReadPrgrp = {
               DbPrgrpResponse(
                   isSuccess = true,
                   data = initPrgrp,
               )
            },
            invokeDeletePrgrp = {
                if (it.id == initPrgrp.id)
                    DbPrgrpResponse(
                        isSuccess = true,
                        data = initPrgrp
                    )
                else DbPrgrpResponse(isSuccess = false, data = null)
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
    fun repoDeleteSuccessTest() = runTest {
        val prgrpToUpdate = PrgrpGroup(
            id = PrgrpGroupId("123"),
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
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initPrgrp.id, ctx.groupResponse.id)
        assertEquals(initPrgrp.name, ctx.groupResponse.name)
        assertEquals(initPrgrp.description, ctx.groupResponse.description)
        assertEquals(initPrgrp.visibility, ctx.groupResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
