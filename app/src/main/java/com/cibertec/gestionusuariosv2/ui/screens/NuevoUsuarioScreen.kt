package com.cibertec.gestionusuariosv2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.cibertec.gestionusuariosv2.ui.components.AppButton
import com.cibertec.gestionusuariosv2.ui.components.AppTextField
import com.cibertec.gestionusuariosv2.ui.components.AppTopBar
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel

@Composable
fun NuevoUsuarioScreen(navController: NavController, viewModel: MainViewModel) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var usuario by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }

    var nombreError by rememberSaveable { mutableStateOf<String?>(null) }
    var usuarioError by rememberSaveable { mutableStateOf<String?>(null) }
    var correoError by rememberSaveable { mutableStateOf<String?>(null) }
    var passError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = { AppTopBar("Nuevo usuario", onBackClick = { navController.popBackStack() }) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(24.dp))
            AppTextField(nombre, { nombre = it; nombreError = null }, "Nombre completo", Icons.Default.Person, errorMessage = nombreError, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            AppTextField(usuario, { usuario = it; usuarioError = null }, "Usuario", Icons.Default.Person, errorMessage = usuarioError, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            AppTextField(correo, { correo = it; correoError = null }, "Correo electrónico", Icons.Default.Email, errorMessage = correoError, keyboardType = KeyboardType.Email, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            AppTextField(pass, { pass = it; passError = null }, "Contraseña", Icons.Default.Lock, isPassword = true, errorMessage = passError, keyboardType = KeyboardType.Password, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(32.dp))
            AppButton("GUARDAR", onClick = {
                var ok = true
                if (nombre.isBlank()) { nombreError = "Ingresa el nombre completo"; ok = false }
                if (usuario.isBlank()) { usuarioError = "Ingresa un usuario"; ok = false }
                if (!correo.contains("@") || correo.isBlank()) { correoError = "Ingresa un correo válido"; ok = false }
                if (pass.length < 6) { passError = "Mínimo 6 caracteres"; ok = false }

                if (ok) {
                    viewModel.addLocalUser(nombre.trim(), usuario.trim(), correo.trim(), pass)
                    navController.popBackStack()
                }
            })
            Spacer(Modifier.height(24.dp))
        }
    }
}