package com.alab.oauthwithvk_example

/**
 * Определяет интерфейс получения и установки параметров аккаунта.
 */
interface IAccountService {
    /**
     * Возвращает или устанавливает токен.
     */
    var token: String?
    /**
     * Возвращает или устанавливает идентификатор пользователя.
     */
    var userId: String?
}