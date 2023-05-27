package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = CpmkCommand.DELETE
    private val processor by lazy { CpmkOrProcessor() }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

