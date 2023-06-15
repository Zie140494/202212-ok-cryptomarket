package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.OrRepoStub
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = CpmkCommand.SEARCH
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = OrRepoStub()
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orFilterRequest = CpmkOrFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(CpmkState.FAILING, ctx.state)
    }
}

