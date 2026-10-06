package com.cibertec.gestionusuariosv2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.data.remote.ApiUser
import com.cibertec.gestionusuariosv2.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class MainViewModel (private val repository: UserRepository) : ViewModel(){

    // Estado para API
    private val _apiUsers = MutableStateFlow<List<ApiUser>>(emptyList())
    val apiUsers: StateFlow<List<ApiUser>> = _apiUsers

    // Estado para Room
    val localUsers: StateFlow<List<UserEntity>> = repository.getLocalUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        fetchApiUsers()
    }

    private fun fetchApiUsers() {
        viewModelScope.launch {
            try {
                _apiUsers.value = repository.getRemoteUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // CRUD Locales
    fun addLocalUser(nombre: String, usuario: String, correo: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertLocalUser(
                UserEntity(nombreCompleto = nombre, usuario = usuario, correo = correo, contrasena = pass)
            )
        }
    }

    fun updateLocalUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateLocalUser(user) }
    }

    fun deleteLocalUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteLocalUser(user) }
    }

    fun getUserById(id: Int, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            onResult(repository.getUserById(id))
        }
    }
}