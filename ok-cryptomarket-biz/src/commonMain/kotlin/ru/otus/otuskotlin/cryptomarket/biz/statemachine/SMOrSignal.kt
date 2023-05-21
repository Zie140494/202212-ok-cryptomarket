package ru.otus.otuskotlin.cryptomarket.biz.statemachine

import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates
import kotlin.time.Duration

data class SMOrSignal(
    val state: SMOrStates,
    val duration: Duration,
    val dollarEqualent: Float,
)
