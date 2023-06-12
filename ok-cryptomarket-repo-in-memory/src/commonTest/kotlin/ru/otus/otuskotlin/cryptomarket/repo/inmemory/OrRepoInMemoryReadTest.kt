package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrReadTest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class OrRepoInMemoryReadTest: RepoOrReadTest() {
    override val repo: IOrRepository = OrRepoInMemory(
        initObjects = initObjects
    )
}
