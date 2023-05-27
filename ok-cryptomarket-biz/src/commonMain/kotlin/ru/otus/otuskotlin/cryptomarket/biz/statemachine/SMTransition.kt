package ru.otus.otuskotlin.cryptomarket.biz.statemachine

import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates


data class SMTransition(
    val state: SMOrStates,
    val description: String,
)
