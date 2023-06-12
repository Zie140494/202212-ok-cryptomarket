package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrCreateTest

class OrRepoInMemoryCreateTest : RepoOrCreateTest() {
    override val repo = OrRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}