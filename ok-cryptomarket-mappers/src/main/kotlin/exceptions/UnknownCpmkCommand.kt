package ru.otus.otuskotlin.cryptomarket.mappers.exceptions

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand


class UnknownCpmkCommand(command: CpmkCommand) : Throwable("Wrong command $command at mapping toTransport stage")
