package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.otuskotlin.cryptomarket.api.models.*


// Temporary simple test with stubs
@WebMvcTest(OrController::class)
internal class OrControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createOr() = testStubOr(
        "/or/create",
        OrCreateRequest(),
        CpmkContext().apply { orResponse = CpmkOrStub.get() }.toTransportCreate()
    )

    @Test
    fun readOr() = testStubOr(
        "/or/read",
        OrReadRequest(),
        CpmkContext().apply { orResponse = CpmkOrStub.get() }.toTransportRead()
    )

    @Test
    fun updateOr() = testStubOr(
        "/or/update",
        OrUpdateRequest(),
        CpmkContext().apply { orResponse = CpmkOrStub.get() }.toTransportUpdate()
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
        CpmkContext().apply { orsResponse.add(CpmkOrStub.get()) }.toTransportSearch()
    )

    private fun <Req : Any, Res : Any> testStubOr(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}
