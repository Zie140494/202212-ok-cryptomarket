package ru.otus.otuskotlin.cryptomarket.biz.statemachine

import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class SMOrStateResolver {
    fun resolve(signal: SMOrSignal): SMTransition {
        require(signal.duration >= 0.milliseconds) { "Publication duration cannot be negative" }
        require(signal.dollarEqualent >= 0) { "View count cannot be negative" }
        val sig = Sig(
            st = signal.state,
            dur = SMDurs.values().first { signal.duration >= it.min && signal.duration < it.max },
            vws = SMValues.values().first { signal.dollarEqualent >= it.min && signal.dollarEqualent < it.max },
        )

        return TR_MX[sig] ?: TR_ERROR
    }

    companion object {
        private enum class SMDurs(val min: Duration, val max: Duration) {
            D_NEW(0.seconds, 3.days),
            D_ACT(3.days, 14.days),
            D_OLD(14.days, Int.MAX_VALUE.seconds),
        }
        private enum class SMValues(val min: Int, val max: Int) { FEW(0, 30), MODER(30, 100), LARGE(100, Int.MAX_VALUE) }
        private data class Sig(
            val st: SMOrStates,
            val dur: SMDurs,
            val vws: SMValues,
        )

        private val TR_MX = mapOf(
            Sig(SMOrStates.NEW, SMDurs.D_NEW, SMValues.FEW) to SMTransition(SMOrStates.NEW, "Новый без изменений"),
            Sig(SMOrStates.NEW, SMDurs.D_ACT, SMValues.FEW) to SMTransition(SMOrStates.ACTUAL, "Вышло время, перевод из нового в актуальное"),
            Sig(SMOrStates.NEW, SMDurs.D_NEW, SMValues.MODER) to SMTransition(
                SMOrStates.VIP,
                "Большая сумма для обмена, VIP ордер"
            ),
            Sig(SMOrStates.NEW, SMDurs.D_NEW, SMValues.LARGE) to SMTransition(
                SMOrStates.VIP,
                "Очень большая сумма для обмена, VIP ордер"
            ),
            Sig(SMOrStates.VIP, SMDurs.D_NEW, SMValues.MODER) to SMTransition(SMOrStates.VIP, "Остается VIP"),
            Sig(SMOrStates.VIP, SMDurs.D_ACT, SMValues.MODER) to SMTransition(
                SMOrStates.ARCHIVE,
                "Время вышло, VIP утих, становится архивным"
            ),
            Sig(SMOrStates.VIP, SMDurs.D_ACT, SMValues.LARGE) to SMTransition(
                SMOrStates.ARCHIVE,
                "Время вышло, VIP становится актуальным"
            ),
            Sig(SMOrStates.NEW, SMDurs.D_OLD, SMValues.FEW) to SMTransition(
                SMOrStates.OLD,
                "Устарело, сумма маленькая, старый ордер"
            ),
        )
        private val TR_ERROR = SMTransition(SMOrStates.ERROR, "Unprovided transition occurred")
    }
}
