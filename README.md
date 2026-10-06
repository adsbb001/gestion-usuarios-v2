# 📱 Gestión de Usuarios v2.0

Aplicación móvil desarrollada con **Jetpack Compose** para el curso
*Desarrollo de Aplicaciones Móviles I*

## ✨ Funcionalidades
- Registro de cuenta y login validados contra **Room (SQLite)**
- Listado de usuarios desde API con **Retrofit** (JSONPlaceholder) en `LazyColumn`
- CRUD completo de usuarios locales: crear, listar, editar y eliminar
- Diálogo de confirmación antes de eliminar
- Búsqueda reactiva en tiempo real (StateFlow + combine)
- Navegación con Bottom Navigation (Home / Locales)

## 🛠️ Stack y arquitectura
- Kotlin + Jetpack Compose (Material 3)
- Room Database + KSP
- Retrofit + Gson + Corrutinas
- **MVVM + Repository Pattern** (interfaz `UserRepository` + `UserRepositoryImpl`)
- StateFlow, ViewModelFactory y Navigation Compose

## 🚀 Ejecución
1. Clona el repositorio
2. Ábrelo en Android Studio
3. Sync Gradle y Run ▶
