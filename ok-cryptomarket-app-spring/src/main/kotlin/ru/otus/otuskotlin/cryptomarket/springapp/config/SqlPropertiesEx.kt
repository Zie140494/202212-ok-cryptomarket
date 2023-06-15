package ru.otus.otuskotlin.cryptomarket.springapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import ru.otus.otuskotlin.cryptomarket.backend.repo.sql.SqlProperties

// Нужна аннотация @ConstructorBinding, ее нельзя поставить над методом c @Bean, не нашел другого пути
@ConfigurationProperties("sql")
class SqlPropertiesEx
@ConstructorBinding
constructor(
    url: String,
    user: String,
    password: String,
    schema: String,
    dropDatabase: Boolean
): SqlProperties(url, user, password, schema, dropDatabase)