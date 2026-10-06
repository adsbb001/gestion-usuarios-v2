package com.cibertec.gestionusuariosv2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.remember

import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.ui.components.AppButton
import com.cibertec.gestionusuariosv2.ui.components.AppDangerOutlineButton
import com.cibertec.gestionusuariosv2.ui.components.AppTextField
import com.cibertec.gestionusuariosv2.ui.components.AppTopBar
import com.cibertec.gestionusuariosv2.ui.components.ConfirmDeleteDialog
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel

@Composable
fun EditarUsuarioScreen(navController: NavController, viewModel: MainViewModel, userId: Int) {
    var user by remember { mutableStateOf<UserEntity?>(null) }
    var cargado by rememberSaveable { mutableStateOf(false) }

    var nombre by rememberSaveable { mutableStateOf("") }
    var usuario by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    // Carga los datos desde Room una sola vez
    LaunchedEffect(userId) {
        viewModel.getUserById(userId) { u ->
            user = u
            nombre = u?.nombreCompleto ?: ""
            usuario = u?.usuario ?: ""
            correo = u?.correo ?: ""
            pass = u?.contrasena ?: ""
            cargado = true
        }
    }

    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            onConfirm = {
                user?.let { viewModel.deleteLocalUser(it) }
                navController.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            AppTopBar(
                title = "Editar usuario",
                onBackClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        if (!cargado) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PurplePrimary)
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(24.dp))
                AppTextField(nombre, { nombre = it }, "Nombre completo", Icons.Default.Person, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                AppTextField(usuario, { usuario = it }, "Usuario", Icons.Default.Person, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                AppTextField(correo, { correo = it }, "Correo electrónico", Icons.Default.Email, keyboardType = KeyboardType.Email, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                AppTextField(pass, { pass = it }, "Contraseña", Icons.Default.Lock, isPassword = true, keyboardType = KeyboardType.Password, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(32.dp))
                AppButton("ACTUALIZAR", onClick = {
                    user?.let { original ->
                        viewModel.updateLocalUser(
                            original.copy(
                                nombreCompleto = nombre.trim(),
                                usuario = usuario.trim(),
                                correo = correo.trim(),
                                contrasena = pass
                            )
                        )
                        navController.popBackStack()
                    }
                })
                Spacer(Modifier.height(12.dp))
                AppDangerOutlineButton("ELIMINAR USUARIO", onClick = { showDeleteDialog = true })
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
