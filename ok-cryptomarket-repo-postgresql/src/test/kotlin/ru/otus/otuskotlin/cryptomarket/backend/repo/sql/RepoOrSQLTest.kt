package ru.otus.otuskotlin.cryptomarket.backend.repo.sql

import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrCreateTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrDeleteTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrReadTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrSearchTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.RepoOrUpdateTest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class RepoAdSQLCreateTest : RepoOrCreateTest() {
    override val repo: IOrRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoOrDeleteTest() {
    override val repo: IOrRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoOrReadTest() {
    override val repo: IOrRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoOrSearchTest() {
    override val repo: IOrRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoOrUpdateTest() {
    override val repo: IOrRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
