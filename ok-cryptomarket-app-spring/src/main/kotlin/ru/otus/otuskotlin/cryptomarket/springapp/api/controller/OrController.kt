package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.mappers.*
import ru.otus.otuskotlin.cryptomarket.springapp.service.CpmkOrBlockingProcessor


@RestController
@RequestMapping("or")
class OrController
    (
    private val processor: CpmkOrBlockingProcessor,
) {
    @PostMapping("create")
    fun createAd(@RequestBody request: OrCreateRequest): OrCreateResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: OrReadRequest): OrReadResponse {
        val context =CpmkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    fun updateAd(@RequestBody request: OrUpdateRequest): OrUpdateResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: OrDeleteRequest): OrDeleteResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: OrSearchRequest): OrSearchResponse {
        val context = CpmkContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
