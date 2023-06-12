package ru.otus.otuskotlin.cryptomarket.biz


import ru.otus.otuskotlin.cryptomarket.biz.validation.*
import ru.otus.otuskotlin.cryptomarket.biz.workers.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrId
import ru.otus.otuskotlin.cryptomarket.cor.rootChain
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.biz.statemachine.computeOrState
import ru.otus.otuskotlin.cryptomarket.cor.chain
import ru.otus.otuskotlin.cryptomarket.biz.general.initRepo
import ru.otus.otuskotlin.cryptomarket.biz.general.operation
import ru.otus.otuskotlin.cryptomarket.biz.general.prepareResult
import ru.otus.otuskotlin.cryptomarket.biz.general.stubs
import ru.otus.otuskotlin.cryptomarket.biz.repo.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState


class CpmkOrProcessor(private val settings: CpmkCorSettings = CpmkCorSettings()) {
    suspend fun exec(ctx: CpmkContext) = BusinessChain.exec(ctx.apply { settings =  this@CpmkOrProcessor.settings})

    companion object {
        private val BusinessChain = rootChain<CpmkContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание ордера", CpmkCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadWalletNumber("Имитация ошибки валидации номера кошелька")
                    stubValidationBadAccountNumber("Имитация ошибки валидации номера счета")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в orValidating") { orValidating = orRequest.deepCopy() }
                    worker("Очистка id") { orValidating.id = CpmkOrId.NONE }
                    worker("Очистка номера кошелька") { orValidating.walletNumber = orValidating.walletNumber.trim() }
                    worker("Очистка номера счета") { orValidating.accountNumber = orValidating.accountNumber.trim() }
                    validateWalletNumberNotEmpty("Проверка, что номер кошелька не пуст")
                    validateWalletNumberHasContent("Проверка символов")
                    validateAccountNumberNotEmpty("Проверка, что номер счета не пуст")
                    validateAccountNumberHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить ордер", CpmkCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в orValidating") { orValidating = orRequest.deepCopy() }
                    worker("Очистка id") { orValidating.id = CpmkOrId(orValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                computeOrState("Вычисление состояния объявления")
            }
            chain {
                title = "Логика чтения"
                repoRead("Чтение объявления из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == CpmkState.RUNNING }
                    handle { orRepoDone = orRepoRead }
                }
            }
            prepareResult("Подготовка ответа")
            operation("Изменить ордер", CpmkCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadWalletNumber("Имитация ошибки валидации номера кошелька")
                    stubValidationBadAccountNumber("Имитация ошибки валидации номера счета")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в orValidating") { orValidating = orRequest.deepCopy() }
                    worker("Очистка id") { orValidating.id = CpmkOrId(orValidating.id.asString().trim()) }
                    worker("Очистка lock") { orValidating.lock = CpmkOrLock(orValidating.lock.asString().trim()) }
                    worker("Очистка номера кошелька") { orValidating.walletNumber = orValidating.walletNumber.trim() }
                    worker("Очистка номера счета") { orValidating.accountNumber = orValidating.accountNumber.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")
                    validateWalletNumberNotEmpty("Проверка на непустой номер кошелька")
                    validateWalletNumberHasContent("Проверка на наличие содержания в номере кошелька")
                    validateAccountNumberNotEmpty("Проверка на непустой номер кошелька")
                    validateAccountNumberHasContent("Проверка на наличие содержания в номере кошелька")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение объявления из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить ордер", CpmkCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в orValidating") {
                        orValidating = orRequest.deepCopy() }
                    worker("Очистка id") { orValidating.id = CpmkOrId(orValidating.id.asString().trim()) }
                    worker("Очистка lock") { orValidating.lock = CpmkOrLock(orValidating.lock.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateLockNotEmpty("Проверка на непустой lock")
                    validateLockProperFormat("Проверка формата lock")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение объявления из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление объявления из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск объявлений", CpmkCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в orFilterValidating") { orFilterValidating = orFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск объявления в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}
