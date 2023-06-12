package ru.otus.otuskotlin.cryptomarket.common.helpers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.exceptions.RepoConcurrencyException
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock

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

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: CpmkError.Level = CpmkError.Level.ERROR,
) = CpmkError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: CpmkOrLock,
    actualLock: CpmkOrLock?,
    exception: Exception? = null,
) = CpmkError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = CpmkError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = CpmkError(
    field = "id",
    message = "Id must not be null or blank"
)
