package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub


@RestController
@RequestMapping("or")
class OrController {

    @PostMapping("create")
    fun createAd(@RequestBody request: OrCreateRequest): OrCreateResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        context.orResponse = CpmkOrStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: OrReadRequest): OrReadResponse {
        val context =CpmkContext()
        context.fromTransport(request)
        context.orResponse = CpmkOrStub.get()
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    fun updateAd(@RequestBody request: OrUpdateRequest): OrUpdateResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        context.orResponse = CpmkOrStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: OrDeleteRequest): OrDeleteResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: OrSearchRequest): OrSearchResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        context.orsResponse.add(CpmkOrStub.get())
        return context.toTransportSearch()
    }
}
