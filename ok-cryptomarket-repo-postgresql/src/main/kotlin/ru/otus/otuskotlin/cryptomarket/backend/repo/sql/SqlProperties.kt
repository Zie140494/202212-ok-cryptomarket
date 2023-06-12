package ru.otus.otuskotlin.cryptomarket.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/cryptomarket",
    val user: String = "postgres",
    val password: String = "cryptomarket-pass",
    val schema: String = "cryptomarket",
    // Удалять таблицы при старте - нужно для тестирования
    val dropDatabase: Boolean = false,
)