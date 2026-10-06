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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cibertec.gestionusuariosv2.ui.components.AppButton
import com.cibertec.gestionusuariosv2.ui.components.AppTextField
import com.cibertec.gestionusuariosv2.ui.components.AuthHeader
import com.cibertec.gestionusuariosv2.ui.components.RojoPeligro
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary
import com.cibertec.gestionusuariosv2.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var usuario by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var usuarioError by rememberSaveable { mutableStateOf<String?>(null) }
    var passError by rememberSaveable { mutableStateOf<String?>(null) }
    var credencialesInvalidas by rememberSaveable { mutableStateOf(false) }

    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()

    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.resetLoginState()
        }
    }

    Scaffold(containerColor = Color(0xFFF5F5F5)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))
            AuthHeader(Icons.Default.Lock, "Iniciar sesión", "Ingresa tus credenciales")
            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = usuario,
                onValueChange = { usuario = it; usuarioError = null; credencialesInvalidas = false },
                label = "Usuario",
                leadingIcon = Icons.Default.Person,
                errorMessage = usuarioError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            AppTextField(
                value = pass,
                onValueChange = { pass = it; passError = null; credencialesInvalidas = false },
                label = "Contraseña",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                errorMessage = passError,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )

            if (credencialesInvalidas) {
                Spacer(Modifier.height(8.dp))
                Text("Usuario o contraseña incorrectos", color = RojoPeligro, fontSize = 13.sp)
            }

            Spacer(Modifier.height(24.dp))
            AppButton("INICIAR SESIÓN", onClick = {
                var ok = true
                if (usuario.isBlank()) { usuarioError = "Ingresa tu usuario"; ok = false }
                if (pass.isBlank()) { passError = "Ingresa tu contraseña"; ok = false }
                if (ok) viewModel.login(usuario.trim(), pass)
            })

            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes cuenta?", color = Color.Gray, fontSize = 14.sp)
                TextButton(onClick = { navController.navigate("registro") }) {
                    Text("Regístrate", fontWeight = FontWeight.Bold, color = PurplePrimary)
                }
            }
        }
    }
}
