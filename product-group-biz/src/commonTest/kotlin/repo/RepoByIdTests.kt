package com.crowdproj.marketplace.product.group.biz.repo

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.repo.tests.PrgrpRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.PrgrpGroupLock
import models.PrgrpVisibility
import repo.DbPrgrpResponse
import kotlin.test.assertEquals

private val initPrgrp = PrgrpGroup(
    id = PrgrpGroupId("123"),
    name = "abc",
    description = "abc",
    visibility = PrgrpVisibility.VISIBLE_PUBLIC,
)
private val repo = PrgrpRepositoryMock(
        invokeReadPrgrp = {
            if (it.id == initPrgrp.id) {
                DbPrgrpResponse(
                    isSuccess = true,
                    data = initPrgrp,
                )
            } else DbPrgrpResponse(
                isSuccess = false,
                data = null,
                errors = listOf(PrgrpError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    PrgrpCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { PrgrpProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: PrgrpCommand) = runTest {
    val ctx = PrgrpContext(
        command = command,
        state = PrgrpState.NONE,
        workMode = PrgrpWorkMode.TEST,
        groupRequest = PrgrpGroup(
            id = PrgrpGroupId("12345"),
            name = "xyz",
            description = "xyz",
            visibility = PrgrpVisibility.VISIBLE_TO_GROUP,
            lock = PrgrpGroupLock("123-234-abc-ABC"),

        ),
    )
    processor.exec(ctx)
    assertEquals(PrgrpState.FAILING, ctx.state)
    assertEquals(PrgrpGroup(), ctx.groupResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
