package ru.otus.otuskotlin.cryptomarket.common.helpers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState

fun Throwable.asCpmkError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CpmkError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun CpmkContext.addError(vararg error: CpmkError) = errors.addAll(error)

fun CpmkContext.fail(error: CpmkError) {
    addError(error)
    state = CpmkState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: CpmkError.Level = CpmkError.Level.ERROR,
) = CpmkError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
