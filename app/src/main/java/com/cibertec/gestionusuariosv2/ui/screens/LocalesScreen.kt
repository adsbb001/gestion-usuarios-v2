package com.cibertec.gestionusuariosv2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController

import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.ui.components.ConfirmDeleteDialog
import com.cibertec.gestionusuariosv2.ui.components.UserCard
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel

@Composable
fun LocalesScreen(navController: NavController, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val localUsers by viewModel.localUsers.collectAsState()
    var userToDelete by rememberSaveable { mutableStateOf<UserEntity?>(null) }

    // Requisito 7: confirmación antes de eliminar
    userToDelete?.let { user ->
        ConfirmDeleteDialog(
            onConfirm = { viewModel.deleteLocalUser(user); userToDelete = null },
            onDismiss = { userToDelete = null }
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xFFF5F5F5),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("nuevo_usuario") },
                containerColor = PurplePrimary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo usuario")
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            Text(
                "Usuarios registrados en tu dispositivo",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            if (localUsers.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no hay usuarios locales.\nToca + para registrar uno.", color = Color.Gray)
                }
            } else {
                LazyColumn {
                    items(localUsers, key = { it.id }) { user ->
                        UserCard(
                            title = user.nombreCompleto.ifBlank { user.usuario },
                            subtitle1 = user.usuario,
                            subtitle2 = user.correo,
                            colorSeed = user.id,
                            showMenu = true,
                            onEditClick = { navController.navigate("editar_usuario/${user.id}") },
                            onDeleteClick = { userToDelete = user }
                        )
                    }
                }
            }
        }
    }
}