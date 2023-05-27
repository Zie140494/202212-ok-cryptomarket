package ru.otus.otuskotlin.cryptomarket.cor.handlers

import ru.otus.otuskotlin.cryptomarket.cor.CorDslMarker
import ru.otus.otuskotlin.cryptomarket.cor.ICorExec
import ru.otus.otuskotlin.cryptomarket.cor.ICorWorkerDsl

class CorWorker<T>(
    walletNumber: String,
    accountNumber: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    private val blockHandle: suspend T.() -> Unit = {},
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(walletNumber, accountNumber, blockOn, blockExcept) {
    override suspend fun handle(context: T) = blockHandle(context)
}

@CorDslMarker
class CorWorkerDsl<T> : CorExecDsl<T>(), ICorWorkerDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorWorker(
        walletNumber = walletNumber,
        accountNumber = accountNumber,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )

}
