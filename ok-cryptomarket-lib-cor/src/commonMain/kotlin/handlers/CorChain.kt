package ru.otus.otuskotlin.cryptomarket.cor.handlers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.otus.otuskotlin.cryptomarket.cor.*

/**
 * Реализация цепочки (chain), которая исполняет свои вложенные цепочки и рабочие
 * в соответствии со стратегией handler
 */
class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit,
    walletNumber: String,
    accountNumber: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(walletNumber, accountNumber, blockOn, blockExcept) {
    override suspend fun handle(context: T) = handler(context, execs)
}

/**
 * Стратегия последовательного исполнения
 */
suspend fun <T> executeSequential(context: T, execs: List<ICorExec<T>>): Unit =
    execs.forEach {
        it.exec(context)
    }

/**
 * Стратегия параллельного исполнения
 */
suspend fun <T> executeParallel(context: T, execs: List<ICorExec<T>>): Unit = coroutineScope {
    execs.forEach {
        launch { it.exec(context) }
    }
}

@CorDslMarker
class CorChainDsl<T>(
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit = ::executeSequential,
) : CorExecDsl<T>(), ICorChainDsl<T> {
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf()
    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    override fun build(): ICorExec<T> = CorChain(
        walletNumber = walletNumber,
        accountNumber = accountNumber,
        execs = workers.map { it.build() },
        handler = handler,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}
