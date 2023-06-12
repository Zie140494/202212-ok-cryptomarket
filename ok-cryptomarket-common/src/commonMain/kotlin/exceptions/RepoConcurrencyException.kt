package ru.otus.otuskotlin.cryptomarket.common.exceptions

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock

class RepoConcurrencyException(expectedLock: CpmkOrLock, actualLock: CpmkOrLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
