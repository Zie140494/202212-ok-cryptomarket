package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrUpdateTest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class OrRepoInMemoryUpdateTest : RepoOrUpdateTest() {
    override val repo: IOrRepository = OrRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
