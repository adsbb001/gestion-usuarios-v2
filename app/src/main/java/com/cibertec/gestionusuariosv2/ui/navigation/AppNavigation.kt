package com.cibertec.gestionusuariosv2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cibertec.gestionusuariosv2.ui.screens.EditarUsuarioScreen
import com.cibertec.gestionusuariosv2.ui.screens.LoginScreen
import com.cibertec.gestionusuariosv2.ui.screens.MainContainerScreen
import com.cibertec.gestionusuariosv2.ui.screens.NuevoUsuarioScreen
import com.cibertec.gestionusuariosv2.ui.screens.RegistroScreen
import com.cibertec.gestionusuariosv2.ui.viewmodel.AuthViewModel
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel


@Composable
fun AppNavigation(authViewModel: AuthViewModel, mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("registro") { RegistroScreen(navController, authViewModel) }
        composable("main") { MainContainerScreen(navController, mainViewModel) }
        composable("nuevo_usuario") { NuevoUsuarioScreen(navController, mainViewModel) }
        composable("editar_usuario/{userId}") { entry ->
            val userId = entry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            EditarUsuarioScreen(navController, mainViewModel, userId)
        }
    }
}