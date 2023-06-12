package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import org.assertj.core.api.Assertions.assertThat
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.backend.repo.sql.RepoOrSQL
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.mappers.*
import ru.otus.otuskotlin.cryptomarket.springapp.config.CorConfig

// Temporary simple test with stubs
@WebFluxTest(OrController::class, CorConfig::class)
internal class OrControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: CpmkOrProcessor

    @MockkBean
    private lateinit var repo: RepoOrSQL

    @Test
    fun createOr() = testStubOr(
        "/or/create",
        OrCreateRequest(),
        CpmkContext().toTransportCreate()
    )

    @Test
    fun readOr() = testStubOr(
        "/or/read",
        OrReadRequest(),
        CpmkContext().toTransportRead()
    )

    @Test
    fun updateOr() = testStubOr(
        "/or/update",
        OrUpdateRequest(),
        CpmkContext().toTransportUpdate()
    )

    @Test
    fun deleteOr() = testStubOr(
        "/or/delete",
        OrDeleteRequest(),
        CpmkContext().toTransportDelete()
    )

    @Test
    fun searchOr() = testStubOr(
        "/or/search",
        OrSearchRequest(),
        CpmkContext().toTransportSearch()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubOr(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
        coVerify { processor.exec(any()) }
    }
}
