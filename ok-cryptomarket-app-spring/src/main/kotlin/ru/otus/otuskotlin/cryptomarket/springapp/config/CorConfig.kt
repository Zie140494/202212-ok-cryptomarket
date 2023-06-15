package ru.otus.otuskotlin.cryptomarket.springapp.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.cryptomarket.backend.repo.sql.RepoOrSQL
import ru.otus.otuskotlin.cryptomarket.backend.repo.sql.SqlProperties
import ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.OrRepoStub
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import ru.otus.otuskotlin.cryptomarket.logging.common.CmLoggerProvider
import ru.otus.otuskotlin.cryptomarket.logging.jvm.cmLoggerLogback
import ru.otus.otuskotlin.cryptomarket.repo.inmemory.OrRepoInMemory

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class CorConfig {
    @Bean
    fun loggerProvider(): CmLoggerProvider = CmLoggerProvider { cmLoggerLogback(it) }

    @Bean
    fun prodRepository(sqlProperties: SqlProperties) = RepoOrSQL(sqlProperties)

    @Bean
    fun testRepository() = OrRepoInMemory()

    @Bean
    fun stubRepository() = OrRepoStub()

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IOrRepository,
        @Qualifier("testRepository") testRepository: IOrRepository,
        @Qualifier("stubRepository") stubRepository: IOrRepository,
    ): CpmkCorSettings = CpmkCorSettings(
        loggerProvider = loggerProvider(),
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository,
    )

    @Bean
    fun cpmkOrProcessor(corSettings: CpmkCorSettings) = CpmkOrProcessor(settings = corSettings)

}
