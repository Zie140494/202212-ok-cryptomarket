package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrDeleteTest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class OrRepoInMemoryDeleteTest : RepoOrDeleteTest() {
    override val repo: IOrRepository = OrRepoInMemory(
        initObjects = initObjects
    )
}
