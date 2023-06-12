package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.OrRepoStub
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import validation.validationLockCorrect
import validation.validationLockEmpty
import validation.validationLockFormat
import validation.validationLockTrim
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = CpmkCommand.UPDATE
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = OrRepoStub()
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @Test fun correctWalletNumber() = validationWalletNumberCorrect(command, processor)
    @Test fun trimWalletNumber() = validationWalletNumberTrim(command, processor)
    @Test fun emptyWalletNumber() = validationWalletNumberEmpty(command, processor)
    @Test fun badSymbolsWalletNumber() = validationWalletNumberSymbols(command, processor)

    @Test fun correctAccountNumber() = validationAccountNumberCorrect(command, processor)
    @Test fun trimAccountNumber() = validationAccountNumberTrim(command, processor)
    @Test fun emptyAccountNumber() = validationAccountNumberEmpty(command, processor)
    @Test fun badSymbolsAccountNumber() = validationAccountNumberSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)


}

