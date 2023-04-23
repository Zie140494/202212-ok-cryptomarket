package ru.otus.otuskotlin.cryptomarket.biz

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub


class CpmkOrProcessor {
    suspend fun exec(ctx: CpmkContext) {
        ctx.orResponse = CpmkOrStub.get()
    }
}
