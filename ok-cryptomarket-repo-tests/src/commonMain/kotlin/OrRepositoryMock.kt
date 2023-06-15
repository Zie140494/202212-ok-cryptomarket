package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import ru.otus.otuskotlin.cryptomarket.common.repo.*

class OrRepositoryMock(
    private val invokeCreateOr: (DbOrRequest) -> DbOrResponse = { DbOrResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadOr: (DbOrIdRequest) -> DbOrResponse = { DbOrResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateOr: (DbOrRequest) -> DbOrResponse = { DbOrResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteOr: (DbOrIdRequest) -> DbOrResponse = { DbOrResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchOr: (DbOrFilterRequest) -> DbOrsResponse = { DbOrsResponse.MOCK_SUCCESS_EMPTY },
): IOrRepository {
    override suspend fun createOr(rq: DbOrRequest): DbOrResponse {
        return invokeCreateOr(rq)
    }

    override suspend fun readOr(rq: DbOrIdRequest): DbOrResponse {
        return invokeReadOr(rq)
    }

    override suspend fun updateOr(rq: DbOrRequest): DbOrResponse {
        return invokeUpdateOr(rq)
    }

    override suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse {
        return invokeDeleteOr(rq)
    }

    override suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse {
        return invokeSearchOr(rq)
    }
}
