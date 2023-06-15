package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.OrRepositoryMock
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrResponse
import kotlin.test.assertEquals

private val initOr = CpmkOr(
    id = CpmkOrId("123"),
    walletNumber = "abc",
    accountNumber = "abc",
    fiatCurrency = CpmkFiatCurrency.RUB,
    cryptoCurrency = CpmkCryptoCurrency.BTC,
    action = CpmkAction.BUY,
)
private val repo = OrRepositoryMock(
        invokeReadOr = {
            if (it.id == initOr.id) {
                DbOrResponse(
                    isSuccess = true,
                    data = initOr,
                )
            } else DbOrResponse(
                isSuccess = false,
                data = null,
                errors = listOf(CpmkError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    CpmkCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { CpmkOrProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: CpmkCommand) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = CpmkOrId("12345"),
            walletNumber = "xyz",
            accountNumber = "01234567890123456789",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),

        ),
    )
    processor.exec(ctx)
    assertEquals(CpmkState.FAILING, ctx.state)
    assertEquals(CpmkOr(), ctx.orResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
