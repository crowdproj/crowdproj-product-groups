package com.crowdproj.marketplace.product.group.biz

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.stubs.PrgrpStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = PrgrpStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleCorrect(command: PrgrpCommand, processor: PrgrpProcessor) = runTest {
    val ctx = PrgrpContext(
        command = command,
        state = PrgrpState.NONE,
        workMode = PrgrpWorkMode.TEST,
        groupRequest = PrgrpGroup(
            id = stub.id,
            name = "abc",
            description = "abc",
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PrgrpState.FAILING, ctx.state)
    assertEquals("abc", ctx.prgrpValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: PrgrpCommand, processor: PrgrpProcessor) = runTest {
    val ctx = PrgrpContext(
        command = command,
        state = PrgrpState.NONE,
        workMode = PrgrpWorkMode.TEST,
        groupRequest = PrgrpGroup(
            id = stub.id,
            name = " \n\t abc \t\n ",
            description = "abc",
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PrgrpState.FAILING, ctx.state)
    assertEquals("abc", ctx.prgrpValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: PrgrpCommand, processor: PrgrpProcessor) = runTest {
    val ctx = PrgrpContext(
        command = command,
        state = PrgrpState.NONE,
        workMode = PrgrpWorkMode.TEST,
        groupRequest = PrgrpGroup(
            id = stub.id,
            name = "",
            description = "abc",
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PrgrpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: PrgrpCommand, processor: PrgrpProcessor) = runTest {
    val ctx = PrgrpContext(
        command = command,
        state = PrgrpState.NONE,
        workMode = PrgrpWorkMode.TEST,
        groupRequest = PrgrpGroup(
            id = PrgrpGroupId("123"),
            name = "!@#$%^&*(),.{}",
            description = "abc",
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PrgrpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}

