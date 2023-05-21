package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = CpmkCommand.UPDATE
    private val processor by lazy { CpmkOrProcessor() }

    @Test fun correctWalletNumber() = validationWalletNumberCorrect(command, processor)
    @Test fun trimWalletNumber() = validationWalletNumberTrim(command, processor)
    @Test fun emptyWalletNumber() = validationWalletNumberEmpty(command, processor)
    @Test fun badSymbolsWalletNumber() = validationWalletNumberSymbols(command, processor)

    @Test fun correctDescription() = validationAccountNumberCorrect(command, processor)
    @Test fun trimDescription() = validationAccountNumberTrim(command, processor)
    @Test fun emptyDescription() = validationAccountNumberEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationAccountNumberSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

