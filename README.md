# Android Development Course - Plotnikov IT-41
Учебный проект по курсу разработки Android-приложений.

---

## Технологии

- **Kotlin** + **Android SDK** (Min 24, Target 36)
- **Jetpack Compose** + Material Design 3
- **MVVM Architecture** (Clean Architecture)
- **Retrofit 2.9.0** + Gson - сетевые запросы
- **Kotlin Coroutines** + StateFlow - асинхронность
- **SQLite** + SharedPreferences - хранение данных
- **OSMDroid** - карты OpenStreetMap

---

## Быстрый старт

### Клонирование
```
git clone https://github.com/TamponN/AndrroidDevelopment_Plotnikov_IT41.git
cd AndrroidDevelopment_Plotnikov_IT41
git checkout master 
```

### Требования
- Android Studio Arctic Fox или новее
- JDK 11+
- Android SDK API 24-36

### Настройка подписи 

- Создайте `keystore.properties` в корне проекта:

```markdown
storeFile=path/to/keystore.jks
storePassword=password
keyAlias=alias
keyPassword=password
```

### Запуск
1. Откройте проект в Android Studio
2. Дождитесь синхронизации Gradle
3. Нажмите Run или `Shift+F10`

