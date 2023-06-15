package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrId

fun ICorChainDsl<CpmkContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в CpmkOrId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    val t = "!@#\$%^&*(),.{}".matches(regExp)
    on { orValidating.id != CpmkOrId.NONE && ! orValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = orValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only letters and numbers"
        )
        )
    }
}
