package com.crowdproj.marketplace.product.group.biz.repo

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.repo.tests.PrgrpRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.PrgrpVisibility
import repo.DbPrgrpsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = PrgrpUserId("321")
    private val command = PrgrpCommand.SEARCH
    private val initPrgrp = PrgrpGroup(
        id = PrgrpGroupId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        visibility = PrgrpVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { PrgrpRepositoryMock(
        invokeSearchPrgrp = {
            DbPrgrpsResponse(
                isSuccess = true,
                data = listOf(initPrgrp),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = PrgrpContext(
            command = command,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.TEST,
            groupFilterRequest = PrgrpGroupFilter(
                searchString = "ab",
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpState.FINISHING, ctx.state)
        assertEquals(1, ctx.groupsResponse.size)
    }
}
