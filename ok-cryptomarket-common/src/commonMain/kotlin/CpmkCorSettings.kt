package ru.otus.otuskotlin.cryptomarket.common

import ru.otus.otuskotlin.cryptomarket.logging.common.CmLoggerProvider
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

data class CpmkCorSettings(
    val loggerProvider: CmLoggerProvider = CmLoggerProvider(),
    val repoStub: IOrRepository = IOrRepository.NONE,
    val repoTest: IOrRepository = IOrRepository.NONE,
    val repoProd: IOrRepository = IOrRepository.NONE,
) {
    companion object {
        val NONE = CpmkCorSettings()
    }
}
