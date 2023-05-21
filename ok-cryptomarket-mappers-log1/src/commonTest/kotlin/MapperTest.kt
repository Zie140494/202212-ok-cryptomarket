import ru.otus.otuskotlin.cryptomarket.api.logs.mapper.toLog
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = CpmkContext(
            requestId = CpmkRequestId("1234"),
            command = CpmkCommand.CREATE,
            orResponse = CpmkOr(
                walletNumber = "walletNumber",
                accountNumber = "accountNumber",
                fiatCurrency = CpmkFiatCurrency.RUB,
                cryptoCurrency = CpmkCryptoCurrency.BTC,
                action = CpmkAction.BUY,
            ),
            errors = mutableListOf(
                CpmkError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = CpmkState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("ok-cryptomarket", log.source)
        assertEquals("1234", log.or?.requestId)
        assertEquals("BUY", log.or?.responseOr?.action)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("ERROR", error?.level)
    }
}
