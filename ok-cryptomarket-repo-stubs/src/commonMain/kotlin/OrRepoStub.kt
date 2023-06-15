package ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory


import ru.otus.otuskotlin.cryptomarket.common.models.CpmkAction
import ru.otus.otuskotlin.cryptomarket.common.repo.*
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub

class OrRepoStub() : IOrRepository {
    override suspend fun createOr(rq: DbOrRequest): DbOrResponse {
        return DbOrResponse(
            data = CpmkOrStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readOr(rq: DbOrIdRequest): DbOrResponse {
        return DbOrResponse(
            data = CpmkOrStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateOr(rq: DbOrRequest): DbOrResponse {
        return DbOrResponse(
            data = CpmkOrStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse {
        return DbOrResponse(
            data = CpmkOrStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse {
        return DbOrsResponse(
            data = CpmkOrStub.prepareSearchList(filter = "", CpmkAction.BUY),
            isSuccess = true,
        )
    }
}
