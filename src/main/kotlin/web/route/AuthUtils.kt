package web.route

import java.util.*

object AuthUtils {
    fun extractCredentials(authHeader: String?): Pair<String, String>? {
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Basic ")) {
            return null
        }
        return try {
            decodeBasicAuth(authHeader)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun decodeBasicAuth(authHeader: String): Pair<String, String>? {
        try {
            // Удаляем префикс "Basic "
            val base64Credentials = authHeader.removePrefix("Basic ")

            // Декодируем Base64
            val decodedBytes = Base64.getDecoder().decode(base64Credentials)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            // Разделяем строку по символу ":"
            val parts = decodedString.split(":", limit = 2)
            println(parts)
            if (parts.size == 2) {
                return Pair(parts[0], parts[1]) // Возвращаем логин и пароль
            }
        } catch (e: Exception) {
            e.printStackTrace() // Логируем ошибку для отладки
        }
        return null // Возвращаем null в случае ошибки
    }
}