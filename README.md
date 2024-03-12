# VkTest
**VkTest** - это мобильное приложение, разработанное для профильного задания на стажировку во ВКонтакте.

## Светлая тема
<img src="https://github.com/Towich/VkTest/assets/100920758/29efec18-2ffb-4de7-9477-53025a3e6998" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/e295df7a-4d56-4399-b7d3-889a30c28cb6" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/716889e0-1f2e-4bd1-8ba7-740ec72b166c" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/5af3ecb2-3232-4b90-966c-eb177fe299bb" width="190">

## Темная тема
<img src="https://github.com/Towich/VkTest/assets/100920758/47239287-0496-46e6-8022-1f742f36bf01" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/91edee19-eafb-4f3f-b442-0750dc7763cf" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/45aaf12e-edfd-48af-830f-3741ed726c51" width="190">
<img src="https://github.com/Towich/VkTest/assets/100920758/eca5ef7c-9ca0-4ece-bfb4-aa6b94cceea7" width="190">

## Основной функционал
* Просмотр списка товаров страницами по 20 элементов (источник данных: <a href="https://dummyjson.com/products" target="_blank">dummyjson.com/products</a>)
* Бесконечный список (при достижении конца списка загружается следующая страница из 20 товаров)
* Просмотр страницы товара
* Сортировка товаров по категориям
* Поиск товаров по названию
* Уведомление пользователя о любых сетевых ошибках при отправке запросов

## Используемые технологии
* Language: Kotlin
* UI: Jetpack Compose
* Architecture: MVVM
* Async: Kotlin Coroutines
* DI: Hilt
* HTTP Client: KTor
* Image loading library: Coil

## Установка
### Способ первый
1. Склонируйте репозиторий в вашу локальную папку: *git clone https://github.com/Towich/VkTest.git*
2. Откройте проект в Android Studio
3. Запустите приложение на эмуляторе или на устройстве

### Способ второй
Скачайте файл [vk-test.apk](https://github.com/Towich/VkTest/blob/master/vk-test.apk) и установите на устройстве/эмуляторе.
