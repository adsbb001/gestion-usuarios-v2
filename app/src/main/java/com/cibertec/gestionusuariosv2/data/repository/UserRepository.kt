package com.cibertec.gestionusuariosv2.data.repository

import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.data.remote.ApiUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // --- Operaciones locales (Room) ---
    fun getLocalUsers(): Flow<List<UserEntity>>
    suspend fun insertLocalUser(user: UserEntity)
    suspend fun updateLocalUser(user: UserEntity)
    suspend fun deleteLocalUser(user: UserEntity)
    suspend fun getUserById(id: Int): UserEntity?
    suspend fun loginUser(user: String, pass: String): UserEntity?

    // --- Operaciones remotas (Retrofit) ---
    suspend fun getRemoteUsers(): List<ApiUser>
}