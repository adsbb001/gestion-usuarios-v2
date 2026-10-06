package com.cibertec.gestionusuariosv2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState

import com.cibertec.gestionusuariosv2.ui.components.UserCard
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary
import com.cibertec.gestionusuariosv2.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val apiUsers by viewModel.apiUsers.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "Datos obtenidos desde JSONPlaceholder",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        if (apiUsers.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PurplePrimary)
            }
        } else {
            LazyColumn {
                items(apiUsers, key = { it.id }) { user ->
                    UserCard(
                        title = user.name,
                        subtitle1 = "@${user.username}",
                        subtitle2 = user.email,
                        colorSeed = user.id
                    )
                }
            }
        }
    }
}