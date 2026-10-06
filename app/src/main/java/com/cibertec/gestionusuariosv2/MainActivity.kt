package com.cibertec.gestionusuariosv2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.cibertec.gestionusuariosv2.data.local.AppDatabase
import com.cibertec.gestionusuariosv2.data.remote.ApiService
import com.cibertec.gestionusuariosv2.data.repository.UserRepository
import com.cibertec.gestionusuariosv2.ui.navigation.AppNavigation
import com.cibertec.gestionusuariosv2.ui.theme.GestionUsuariosV2Theme
import com.cibertec.gestionusuariosv2.ui.viewmodel.AuthViewModel
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel
import com.cibertec.gestionusuariosv2.data.repository.UserRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    // Instancias únicas
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()
    }
    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    private val repository: UserRepository by lazy {
        UserRepositoryImpl(database.userDao(), apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fábricas para pasar el repositorio a los ViewModels
        val authViewModel = AuthViewModel(repository)
        val mainViewModel = MainViewModel(repository)

        setContent {
            GestionUsuariosV2Theme {
                // Pasamos los viewmodels a la navegación
                AppNavigation(authViewModel, mainViewModel)
            }
        }
    }
}