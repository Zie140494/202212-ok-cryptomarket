package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.OrRepoStub
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = CpmkCommand.READ
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = OrRepoStub()
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

}

