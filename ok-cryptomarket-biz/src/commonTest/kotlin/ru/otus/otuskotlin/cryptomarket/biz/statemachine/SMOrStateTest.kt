package ru.otus.otuskotlin.cryptomarket.biz.statemachine

import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days

class SMOrStateTest {

    @Test
    fun new2actual() {
        val machine = SMOrStateResolver()
        val signal = SMOrSignal(
            state = SMOrStates.NEW,
            duration = 4.days,
            dollarEqualent = 20F,
        )
        val transition = machine.resolve(signal)
        assertEquals(SMOrStates.ACTUAL, transition.state)
        assertContains(transition.description, "актуальное", ignoreCase = true)
    }

    @Test
    fun new2hit() {
        val machine = SMOrStateResolver()
        val signal = SMOrSignal(
            state = SMOrStates.NEW,
            duration = 2.days,
            dollarEqualent = 101F,
        )
        val transition = machine.resolve(signal)
        assertEquals(SMOrStates.VIP, transition.state)
        assertContains(transition.description, "Очень", ignoreCase = true)
    }
}
