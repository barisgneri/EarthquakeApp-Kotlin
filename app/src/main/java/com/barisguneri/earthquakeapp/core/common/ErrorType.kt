package com.barisguneri.earthquakeapp.core.common

/**
 * Karşılaşılabilecek farklı hata türlerini sınıflandırmak için kullanılır.
 */
sealed class ErrorType {
    /**
     * Cihazda internet bağlantısı olmadığında oluşan hata.
     */
    object NoInternetConnection : ErrorType()

    //{"status":false,"httpStatus":429,"desc":"Too Many Request in 1 minute! Requests limited in 1 minute maximum 40 times"}
    object TooManyRequests : ErrorType()

    /**
     * API'den 2xx dışında bir HTTP durum kodu (404, 500, 401 vb.) döndüğünde oluşur.
     * @param code HTTP durum kodu.
     * @param message Sunucudan gelen hata mesajı (olabilirse).
     */
    data class HttpError(val code: Int, val message: String?) : ErrorType()

    /**
     * Sunucunun kendi iş mantığına göre döndürdüğü hatalar.
     * Örneğin, HTTP 200 OK dönüp JSON içinde "status": "error" gibi bir alanla hata bildirmesi.
     * @param apiCode API'nin belirlediği özel hata kodu.
     * @param message API'nin döndüğü hata mesajı.
     */
    data class ApiError(val apiCode: Int?, val message: String?) : ErrorType()

    /**
     * Gelen JSON verisinin beklenen modele dönüştürülemediği (parse edilemediği) durumlarda oluşur.
     */
    object SerializationError : ErrorType()

    /**
     * Beklenmedik, tanımlanamayan diğer tüm hatalar için kullanılır.
     * @param throwable Orijinal hata nesnesi (loglama için).
     */
    data class Unknown(val apiCode: Int?, val message: String?) : ErrorType()
}

data class PagingException(val errorType: ErrorType) : Exception()