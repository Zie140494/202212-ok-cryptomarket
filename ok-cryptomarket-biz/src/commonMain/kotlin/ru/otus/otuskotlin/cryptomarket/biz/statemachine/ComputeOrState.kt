package ru.otus.otuskotlin.cryptomarket.biz.statemachine

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.NONE
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import kotlin.reflect.KClass

private val machine = SMOrStateResolver()
private val clazz: KClass<*> = ICorChainDsl<CpmkContext>::computeOrState::class
fun ICorChainDsl<CpmkContext>.computeOrState(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    this.accountNumber = "Вычисление состояния объявления"
    on { state == CpmkState.RUNNING }
    handle {
        val log = settings.loggerProvider.logger(clazz)
        val timeNow = Clock.System.now()
        val or = orValidated
        val prevState = or.orState
        val timePublished = or.timeCreated.takeIf { it != Instant.NONE } ?: timeNow
        val signal = SMOrSignal(
            state = prevState.takeIf { it != SMOrStates.NONE } ?: SMOrStates.NEW,
            duration = timeNow - timePublished,
            dollarEqualent = or.dollarEqualent,
        )
        val transition = machine.resolve(signal)
        if (transition.state != prevState) {
            log.info("New or state transition: ${transition.description}")
        }
        or.orState = transition.state
    }
}
