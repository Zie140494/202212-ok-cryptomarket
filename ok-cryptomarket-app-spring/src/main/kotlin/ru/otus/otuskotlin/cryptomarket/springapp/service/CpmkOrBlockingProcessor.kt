package ru.otus.otuskotlin.cryptomarket.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext

@Service
class CpmkOrBlockingProcessor {
    private val processor = CpmkOrProcessor()

    fun exec(ctx: CpmkContext) = runBlocking { processor.exec(ctx) }
}