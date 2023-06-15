package ru.otus.otuskotlin.cryptomarket.common.repo

import ru.otus.otuskotlin.cryptomarket.common.helpers.errorEmptyId as cpmkErrorEmptyId
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorNotFound as cpmkErrorNotFound
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOr
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError

data class DbOrResponse(
    override val data: CpmkOr?,
    override val isSuccess: Boolean,
    override val errors: List<CpmkError> = emptyList()
): IDbResponse<CpmkOr> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbOrResponse(null, true)
        fun success(result: CpmkOr) = DbOrResponse(result, true)
        fun error(errors: List<CpmkError>, data: CpmkOr? = null) = DbOrResponse(data, false, errors)
        fun error(error: CpmkError, data: CpmkOr? = null) = DbOrResponse(data, false, listOf(error))

        val errorEmptyId = error(cpmkErrorEmptyId)

        fun errorConcurrent(lock: CpmkOrLock, or: CpmkOr?) = error(
            errorRepoConcurrency(lock, or?.lock?.let { CpmkOrLock(it.asString()) }),
            or
        )

        val errorNotFound = error(cpmkErrorNotFound)
    }
}
