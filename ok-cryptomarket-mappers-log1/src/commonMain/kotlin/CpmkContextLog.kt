package ru.otus.otuskotlin.cryptomarket.api.logs.mapper

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.cryptomarket.api.logs.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*


fun CpmkContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-cryptomarket",
    or = toCpmkLog(),
    errors = errors.map { it.toLog() },
)

fun CpmkContext.toCpmkLog():CpmkLogModel? {
    val orNone = CpmkOr()
    return CpmkLogModel(
        requestId = requestId.takeIf { it != CpmkRequestId.NONE }?.asString(),
        requestOr = orRequest.takeIf { it != orNone }?.toLog(),
        responseOr = orResponse.takeIf { it != orNone }?.toLog(),
        responseOrs = orsResponse.takeIf { it.isNotEmpty() }?.filter { it != orNone }?.map { it.toLog() },
        requestFilter = orFilterRequest.takeIf { it != CpmkOrFilter() }?.toLog(),
    ).takeIf { it != CpmkLogModel() }
}

private fun CpmkOrFilter.toLog() = OrFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != CpmkUserId.NONE }?.asString(),
    action = action.takeIf { it != CpmkAction.NONE }?.name,
)

fun CpmkError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun CpmkOr.toLog() = OrLog(
    id = id.takeIf { it != CpmkOrId.NONE }?.asString(),
    walletNumber = walletNumber.takeIf { it.isNotBlank() },
    accountNumber = accountNumber.takeIf { it.isNotBlank() },
    fiatCurrency = fiatCurrency.takeIf { it != CpmkFiatCurrency.NONE }?.name,
    cryptoCurrency = cryptoCurrency.takeIf { it != CpmkCryptoCurrency.NONE }?.name,
    action = action.takeIf { it != CpmkAction.NONE }?.name,
    value = value.takeIf { it != 0F }.toString(),
    ownerId = ownerId.takeIf { it != CpmkUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
