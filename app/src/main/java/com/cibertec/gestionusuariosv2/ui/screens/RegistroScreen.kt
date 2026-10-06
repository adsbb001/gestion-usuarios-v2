package com.cibertec.gestionusuariosv2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cibertec.gestionusuariosv2.ui.components.AppButton
import com.cibertec.gestionusuariosv2.ui.components.AppTextField
import com.cibertec.gestionusuariosv2.ui.components.AuthHeader
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary
import com.cibertec.gestionusuariosv2.ui.viewmodel.AuthViewModel

@Composable
fun RegistroScreen(navController: NavController, viewModel: AuthViewModel) {
    var usuario by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var confirmPass by rememberSaveable { mutableStateOf("") }

    var usuarioError by rememberSaveable { mutableStateOf<String?>(null) }
    var correoError by rememberSaveable { mutableStateOf<String?>(null) }
    var passError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(containerColor = Color(0xFFF5F5F5)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            AuthHeader(Icons.Default.PersonAdd, "Crear cuenta", "Completa tus datos para registrarte")
            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = usuario,
                onValueChange = { usuario = it; usuarioError = null },
                label = "Usuario",
                leadingIcon = Icons.Default.Person,
                errorMessage = usuarioError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(
                value = correo,
                onValueChange = { correo = it; correoError = null },
                label = "Correo electrónico",
                leadingIcon = Icons.Default.Email,
                errorMessage = correoError,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(
                value = pass,
                onValueChange = { pass = it; passError = null },
                label = "Contraseña",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                errorMessage = passError,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(
                value = confirmPass,
                onValueChange = { confirmPass = it; confirmError = null },
                label = "Confirmar contraseña",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                errorMessage = confirmError,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            AppButton("REGISTRARME", onClick = {
                var ok = true
                if (usuario.isBlank()) { usuarioError = "Ingresa un usuario"; ok = false }
                if (!correo.contains("@") || correo.isBlank()) { correoError = "Ingresa un correo válido"; ok = false }
                if (pass.length < 6) { passError = "Mínimo 6 caracteres"; ok = false }
                if (pass != confirmPass) { confirmError = "Las contraseñas no coinciden"; ok = false }

                if (ok) {
                    viewModel.register(nombre = "", usuario = usuario.trim(), correo = correo.trim(), pass = pass)
                    navController.popBackStack() // Vuelve al login para iniciar sesión
                }
            })

            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿Ya tienes cuenta?", color = Color.Gray, fontSize = 14.sp)
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Inicia sesión", fontWeight = FontWeight.Bold, color = PurplePrimary)
                }
            }
        }
    }
}