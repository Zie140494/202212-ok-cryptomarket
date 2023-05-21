package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = CpmkCommand.CREATE
    private val processor by lazy { CpmkOrProcessor() }

    @Test fun correctWalletNumber() = validationWalletNumberCorrect(command, processor)
    @Test fun trimWalletNumber() = validationWalletNumberTrim(command, processor)
    @Test fun emptyWalletNumber() = validationWalletNumberEmpty(command, processor)
    @Test fun badSymbolsWalletNumber() = validationWalletNumberSymbols(command, processor)

    @Test fun correctAccountNumber() = validationAccountNumberCorrect(command, processor)
    @Test fun trimAccountNumber() = validationAccountNumberTrim(command, processor)
    @Test fun emptyAccountNumber() = validationAccountNumberEmpty(command, processor)
    @Test fun badSymbolsAccountNumber() = validationAccountNumberSymbols(command, processor)

}

