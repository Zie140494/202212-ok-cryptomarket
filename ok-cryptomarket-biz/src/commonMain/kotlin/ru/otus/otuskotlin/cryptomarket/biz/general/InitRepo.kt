package ru.otus.otuskotlin.cryptomarket.biz.general

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorAdministration
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkWorkMode
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        orRepo = when {
            workMode == CpmkWorkMode.TEST -> settings.repoTest
            workMode == CpmkWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != CpmkWorkMode.STUB && orRepo == IOrRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
