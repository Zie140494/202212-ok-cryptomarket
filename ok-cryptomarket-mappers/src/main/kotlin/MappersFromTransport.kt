package ru.otus.otuskotlin.cryptomarket.mappers

import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs
import ru.otus.otuskotlin.cryptomarket.mappers.exceptions.UnknownRequestClass
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock


fun CpmkContext.fromTransport(request: IRequest) = when (request) {
    is OrCreateRequest -> fromTransport(request)
    is OrReadRequest -> fromTransport(request)
    is OrUpdateRequest -> fromTransport(request)
    is OrDeleteRequest -> fromTransport(request)
    is OrSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toOrId() = this?.let { CpmkOrId(it) } ?: CpmkOrId.NONE
private fun String?.toOrWithId() = CpmkOr(id = this.toOrId())
private fun String?.toOrLock() = this?.let { CpmkOrLock(it) } ?: CpmkOrLock.NONE
private fun IRequest?.requestId() = this?.requestId?.let { CpmkRequestId(it) } ?: CpmkRequestId.NONE
private fun OrDebug?.transportToWorkMode(): CpmkWorkMode = when (this?.mode) {
    OrRequestDebugMode.PROD -> CpmkWorkMode.PROD
    OrRequestDebugMode.TEST -> CpmkWorkMode.TEST
    OrRequestDebugMode.STUB -> CpmkWorkMode.STUB
    null -> CpmkWorkMode.PROD
}

private fun OrDebug?.transportToStubCase(): CpmkStubs = when (this?.stub) {
    OrRequestDebugStubs.SUCCESS -> CpmkStubs.SUCCESS
    OrRequestDebugStubs.NOT_FOUND -> CpmkStubs.NOT_FOUND
    OrRequestDebugStubs.BAD_ACCOUNT_NUMBER -> CpmkStubs.BAD_ACCOUNT_NUMBER
    OrRequestDebugStubs.BAD_WALLET -> CpmkStubs.BAD_WALLET
    OrRequestDebugStubs.BAD_CRYPTO_CURRENCY -> CpmkStubs.BAD_CRYPTOCURRENCY
    OrRequestDebugStubs.BAD_FIAT_CURRENCY -> CpmkStubs.BAD_FIAT_CURRENCY
    OrRequestDebugStubs.CANNOT_DELETE -> CpmkStubs.CANNOT_DELETE
    OrRequestDebugStubs.BAD_SEARCH_STRING -> CpmkStubs.BAD_SEARCH_STRING
    null -> CpmkStubs.NONE
}

fun CpmkContext.fromTransport(request: OrCreateRequest) {
    command = CpmkCommand.CREATE
    requestId = request.requestId()
    orRequest = request.or?.toInternal() ?: CpmkOr()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CpmkContext.fromTransport(request: OrReadRequest) {
    command = CpmkCommand.READ
    requestId = request.requestId()
    orRequest = request.or?.id.toOrWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CpmkContext.fromTransport(request: OrUpdateRequest) {
    command = CpmkCommand.UPDATE
    requestId = request.requestId()
    orRequest = request.or?.toInternal() ?: CpmkOr()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CpmkContext.fromTransport(request: OrDeleteRequest) {
    command = CpmkCommand.DELETE
    requestId = request.requestId()
    orRequest = request.or?.id.toOrWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CpmkContext.fromTransport(request: OrSearchRequest) {
    command = CpmkCommand.SEARCH
    requestId = request.requestId()
    orFilterRequest = request.orFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OrSearchFilter?.toInternal(): CpmkOrFilter = CpmkOrFilter(
    searchString = this?.searchString ?: ""
)

private fun OrCreateObject.toInternal(): CpmkOr = CpmkOr(
    accountNumber = this.accountNumber ?: "",
    walletNumber = this.walletNumber ?: "",
    fiatCurrency = this.fiatCurrency.fromTransport(),
    cryptoCurrency = this.cryptoCurrency.fromTransport(),
    action = this.action.fromTransport(),
    value = this.value ?: 0F
)

private fun OrUpdateObject.toInternal(): CpmkOr = CpmkOr(
    id = this.id.toOrId(),
    accountNumber = this.accountNumber ?: "",
    walletNumber = this.walletNumber ?: "",
    fiatCurrency = this.fiatCurrency.fromTransport(),
    cryptoCurrency = this.cryptoCurrency.fromTransport(),
    action = this.action.fromTransport(),
    value = this.value ?: 0F
)

private fun FiatCurrency?.fromTransport(): CpmkFiatCurrency = when (this) {
    FiatCurrency.RUB -> CpmkFiatCurrency.RUB
    null -> CpmkFiatCurrency.NONE
}

private fun CryptoCurrency?.fromTransport(): CpmkCryptoCurrency = when (this) {
    CryptoCurrency.BTC -> CpmkCryptoCurrency.BTC
    null -> CpmkCryptoCurrency.NONE
}

private fun Action?.fromTransport(): CpmkAction = when (this) {
    Action.BUY -> CpmkAction.BUY
    Action.SELL -> CpmkAction.SELL
    null -> CpmkAction.NONE
}

