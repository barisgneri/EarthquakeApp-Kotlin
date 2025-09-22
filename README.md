# EarthquakeApp-Kotlin

EarthquakeApp-Kotlin, Türkiye ve çevresindeki son depremleri listeleyen ve harita üzerinde gösteren modern bir Android uygulamasıdır. Kullanıcılar depremleri büyüklük, zaman aralığı ve sıralama ölçütlerine göre filtreleyebilir ve depremlerin detaylarını görüntüleyebilirler.

## ✨ Özellikler

* **Anlık Deprem Verileri:** Kandilli Rasathanesi ve Deprem Araştırma Enstitüsü'nden alınan güncel deprem verilerini gösterir.
* **Liste Görünümü:** Depremleri, büyüklüklerine göre renklendirilmiş bir liste halinde sunar.
* **Harita Görünümü:** Deprem merkez üslerini interaktif bir harita üzerinde gösterir.
* **Detay Ekranı:** Her deprem için derinlik, en yakın şehirler, havalimanları ve nüfus bilgileri gibi ayrıntılı bilgiler sunar.
* **Filtreleme ve Sıralama:**
    * Büyüklüğe göre filtreleme.
    * Son 1 saat, 12 saat, 24 saat ve 7 gün gibi zaman aralıklarına göre filtreleme.
    * Tarihe veya büyüklüğe göre sıralama.
* **Arama:** Deprem listesinde lokasyon bazlı arama yapma imkanı.
* **Paging:** Sonsuz kaydırma (infinite scroll) ile verimli veri yüklemesi.
* **Çevrimdışı Destek:** Veriler yerel bir veritabanında saklanarak çevrimdışı erişim sağlanır.

## 🏛️ Mimarî

Proje, modern Android uygulama geliştirme prensiplerine uygun olarak **MVI (Model-View-Intent)** mimarisi üzerine inşa edilmiştir. MVI, tek yönlü veri akışını (Unidirectional Data Flow) zorunlu kılarak state yönetimini basitleştirir ve uygulamanın daha öngörülebilir ve test edilebilir olmasını sağlar.

* **Model:** Uygulamanın durumunu (state) temsil eder (`UiState`).
* **View:** Kullanıcı arayüzünü oluşturur ve kullanıcı eylemlerini (intent) yakalar.
* **Intent:** Kullanıcı tarafından gerçekleştirilen eylemleri (`UiAction`) temsil eder ve ViewModel'e iletilir.

ViewModel, `UiAction`'ları işleyerek `UiState`'i günceller ve tek seferlik olaylar için `UiEffect`'ler yayınlar.

## 🛠️ Kullanılan Teknolojiler

* **[Kotlin](https://kotlinlang.org/)**: Ana programlama dili.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern ve deklaratif kullanıcı arayüzü (UI) oluşturmak için kullanıldı.
* **[Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html)**: Asenkron işlemleri ve reaktif programlamayı yönetmek için kullanıldı.
* **[Hilt](https://dagger.dev/hilt/)**: Bağımlılık enjeksiyonu (Dependency Injection) için kullanıldı.
* **[Retrofit & OkHttp](https://square.github.io/retrofit/)**: Ağ (network) istekleri için kullanıldı.
* **[Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**: Uygulama içi navigasyonu yönetmek için kullanıldı.
* **[Room](https://developer.android.com/topic/libraries/architecture/room)**: Verileri yerel olarak saklamak ve filtreleme için paging verisinden yararlanmak için kullanıldı.
* **[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)**: Büyük veri setlerini verimli bir şekilde listelemek ve RemoteMediator ile Room kaydı için kullanıldı.
* **[OSMDroid](https://github.com/osmdroid/osmdroid)**: Harita özelliklerini sağlamak için kullanıldı.

## Ekranlar - Dark

| Splash | List | Map | Detail |
| ------ | ---- | ------ |------ |
|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_103959.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104009.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104131.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104226.png" width="250" height="500"/>|

</br>

## Ekranlar - Light

| Splash | List | Map | Detail
| --- | ------- | ------- |------ |
|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104303.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104330.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104423.png" width="250" height="500"/>|<img src="https://github.com/barisgneri/EarthquakeApp-Kotlin/blob/main/screenshot/Screenshot_20250922_104447.png" width="250" height="500"/>|
