package ru.otus.otuskotlin.cryptomarket.common.statemachine


@Suppress("unused")
enum class SMOrStates {
    NONE, // не инициализировано состояние
    DRAFT,    // черновик
    NEW,      // только что созданное
    ACTUAL,   // актуальное
    ARCHIVE,  // архивное объявление
    OLD,      // старое объявление
    VIP,      // новое объявление с большим числом просмотров

    ERROR,    // ошибки вычисления;

}
