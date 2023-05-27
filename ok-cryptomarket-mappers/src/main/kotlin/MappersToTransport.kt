package ru.otus.otuskotlin.cryptomarket.mappers

import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.mappers.exceptions.UnknownCpmkCommand


fun CpmkContext.toTransportOr(): IResponse = when (val cmd = command) {
    CpmkCommand.CREATE -> toTransportCreate()
    CpmkCommand.READ -> toTransportRead()
    CpmkCommand.UPDATE -> toTransportUpdate()
    CpmkCommand.DELETE -> toTransportDelete()
    CpmkCommand.SEARCH -> toTransportSearch()
    CpmkCommand.NONE -> throw UnknownCpmkCommand(cmd)
}

fun CpmkContext.toTransportCreate() = OrCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CpmkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    or = orResponse.toTransportOr(),
    responseType = "create"
)

fun CpmkContext.toTransportRead() = OrReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CpmkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    or = orResponse.toTransportOr(),
    responseType = "read"
)

fun CpmkContext.toTransportUpdate() = OrUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CpmkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    or = orResponse.toTransportOr(),
    responseType = "update"
)

fun CpmkContext.toTransportDelete() = OrDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CpmkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    or = orResponse.toTransportOr(),
    responseType = "delete"
)

fun CpmkContext.toTransportSearch() = OrSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CpmkState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ors = orsResponse.toTransportOr(),
    responseType = "search"
)


fun List<CpmkOr>.toTransportOr(): List<OrResponseObject>? = this
    .map { it.toTransportOr() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CpmkOr.toTransportOr(): OrResponseObject = OrResponseObject(
    id = id.takeIf { it != CpmkOrId.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != CpmkUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportOr(),
    accountNumber = accountNumber.takeIf { it.isNotBlank() },
    walletNumber = walletNumber.takeIf { it.isNotBlank() },
    fiatCurrency = fiatCurrency.toTransportOr(),
    cryptoCurrency = cryptoCurrency.toTransportOr(),
    action = action.toTransportOr(),
    value = value
)

private fun Set<CpmkOrPermissionClient>.toTransportOr(): Set<OrPermissions>? = this
    .map { it.toTransportOr() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun CpmkOrPermissionClient.toTransportOr() = when (this) {
    CpmkOrPermissionClient.READ -> OrPermissions.READ
    CpmkOrPermissionClient.UPDATE -> OrPermissions.UPDATE
    CpmkOrPermissionClient.DELETE -> OrPermissions.DELETE
}

private fun CpmkFiatCurrency.toTransportOr(): FiatCurrency? = when (this) {
    CpmkFiatCurrency.RUB -> FiatCurrency.RUB
    CpmkFiatCurrency.NONE -> null
}

private fun CpmkCryptoCurrency.toTransportOr(): CryptoCurrency? = when (this) {
    CpmkCryptoCurrency.BTC -> CryptoCurrency.BTC
    CpmkCryptoCurrency.NONE -> null
}

private fun CpmkAction.toTransportOr(): Action? = when (this) {
    CpmkAction.SELL -> Action.SELL
    CpmkAction.BUY -> Action.BUY
    CpmkAction.NONE -> null
}

private fun List<CpmkError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportOr() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CpmkError.toTransportOr() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
