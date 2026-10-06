package com.cibertec.gestionusuariosv2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers


class AuthViewModel (private val repository: UserRepository): ViewModel(){

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    fun register(nombre: String, usuario: String, correo: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {          // ← IO
            val newUser = UserEntity(
                nombreCompleto = nombre,
                usuario = usuario,
                correo = correo,
                contrasena = pass
            )
            repository.insertLocalUser(newUser)
        }
    }

    fun login(usuario: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {          // ← IO
            try {
                val user = repository.loginUser(usuario, pass)
                _loginResult.value = (user != null)
            } catch (e: Exception) {
                e.printStackTrace()
                _loginResult.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginResult.value = null
    }
}