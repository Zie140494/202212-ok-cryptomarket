package ru.otus.otuskotlin.cryptomarket.common.repo

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOr
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError

data class DbOrsResponse(
    override val data: List<CpmkOr>?,
    override val isSuccess: Boolean,
    override val errors: List<CpmkError> = emptyList(),
): IDbResponse<List<CpmkOr>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbOrsResponse(emptyList(), true)
        fun success(result: List<CpmkOr>) = DbOrsResponse(result, true)
        fun error(errors: List<CpmkError>) = DbOrsResponse(null, false, errors)
        fun error(error: CpmkError) = DbOrsResponse(null, false, listOf(error))
    }
}
