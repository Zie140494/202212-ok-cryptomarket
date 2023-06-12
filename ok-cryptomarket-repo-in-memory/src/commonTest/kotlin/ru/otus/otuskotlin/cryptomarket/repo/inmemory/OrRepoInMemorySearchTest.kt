package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrSearchTest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class OrRepoInMemorySearchTest : RepoOrSearchTest() {
    override val repo: IOrRepository = OrRepoInMemory(
        initObjects = initObjects
    )
}
