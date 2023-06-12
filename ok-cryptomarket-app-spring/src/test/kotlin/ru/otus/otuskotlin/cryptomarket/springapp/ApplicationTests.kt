package ru.otus.otuskotlin.cryptomarket.springapp

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.sql.RepoOrSQL

@SpringBootTest
class ApplicationTests {

    @MockkBean
    private lateinit var repo: RepoOrSQL

    @Test
    fun contextLoads() {
    }
}
