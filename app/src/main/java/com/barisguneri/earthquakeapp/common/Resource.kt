package com.barisguneri.earthquakeapp.common

/**
 * Ağ isteklerinin durumunu yönetmek için genel bir sealed class.
 * @param T Başarılı durumda beklenen veri tipi.
 */
sealed class Resource<T> {

    /**
     * Başarılı veri alımı durumunu temsil eder.
     * @param data API'den başarıyla alınan veri.
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * Hata durumunu temsil eder.
     * @param errorType Hatanın detayını içeren tip.
     */
    data class Error<T>(val errorType: ErrorType) : Resource<T>()

    /**
     * Verinin yüklenmekte olduğu durumu temsil eder.
     */
    class Loading<T> : Resource<T>()

}