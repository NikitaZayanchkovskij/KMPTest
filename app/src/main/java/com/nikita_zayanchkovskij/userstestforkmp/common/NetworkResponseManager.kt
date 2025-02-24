package com.nikita_zayanchkovskij.userstestforkmp.common

/** Этот класс принимает в себя ответ с сервера (JsonPlaceHolder) и:
 * 1) В случае, если данные успешно получены - передаёт данные (пользователей).
 * 2) В случае, если в процессе получения данных произошла ошибка: пропал интернет,
 * данные не были получены и т.д. - возвращает пустоту и сообщение об ошибке.
 * 3) Пока идёт загрузка показывает загрузку.
 */
sealed class NetworkResponseManager<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): NetworkResponseManager<T>(data)
    class Error<T>(message: String?, data: T? = null): NetworkResponseManager<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): NetworkResponseManager<T>(data = null, message = null)
}