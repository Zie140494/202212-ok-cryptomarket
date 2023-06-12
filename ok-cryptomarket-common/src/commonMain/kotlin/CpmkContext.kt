package ru.otus.otuskotlin.cryptomarket.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

data class CpmkContext(
    var command: CpmkCommand = CpmkCommand.NONE,
    var state: CpmkState = CpmkState.NONE,
    val errors: MutableList<CpmkError> = mutableListOf(),
    var settings: CpmkCorSettings = CpmkCorSettings.NONE,

    var workMode: CpmkWorkMode = CpmkWorkMode.PROD,
    var stubCase: CpmkStubs = CpmkStubs.NONE,

    var orRepo: IOrRepository = IOrRepository.NONE,
    var orRepoRead: CpmkOr = CpmkOr(),
    var orRepoPrepare: CpmkOr = CpmkOr(),
    var orRepoDone: CpmkOr = CpmkOr(),
    var orsRepoDone: MutableList<CpmkOr> = mutableListOf(),

    var requestId: CpmkRequestId = CpmkRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var orRequest: CpmkOr = CpmkOr(),
    var orFilterRequest: CpmkOrFilter = CpmkOrFilter(),

    var orValidating: CpmkOr = CpmkOr(),
    var orFilterValidating: CpmkOrFilter = CpmkOrFilter(),

    var orValidated: CpmkOr = CpmkOr(),
    var orFilterValidated: CpmkOrFilter = CpmkOrFilter(),


    var orResponse: CpmkOr = CpmkOr(),
    var orsResponse: MutableList<CpmkOr> = mutableListOf(),
)
