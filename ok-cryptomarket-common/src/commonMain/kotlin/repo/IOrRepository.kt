package ru.otus.otuskotlin.cryptomarket.common.repo

interface IOrRepository {
    suspend fun createOr(rq: DbOrRequest): DbOrResponse
    suspend fun readOr(rq: DbOrIdRequest): DbOrResponse
    suspend fun updateOr(rq: DbOrRequest): DbOrResponse
    suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse
    suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse
    companion object {
        val NONE = object : IOrRepository {
            override suspend fun createOr(rq: DbOrRequest): DbOrResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readOr(rq: DbOrIdRequest): DbOrResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateOr(rq: DbOrRequest): DbOrResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
