package com.cibertec.gestionusuariosv2.data.repository

import com.cibertec.gestionusuariosv2.data.local.UserDao
import com.cibertec.gestionusuariosv2.data.local.UserEntity
import com.cibertec.gestionusuariosv2.data.remote.ApiService
import com.cibertec.gestionusuariosv2.data.remote.ApiUser
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val apiService: ApiService
) : UserRepository {

    override fun getLocalUsers(): Flow<List<UserEntity>> = userDao.obtenerTodosLosUsuarios()

    override suspend fun insertLocalUser(user: UserEntity) = userDao.insertarUsuario(user)

    override suspend fun updateLocalUser(user: UserEntity) = userDao.actualizarUsuario(user)

    override suspend fun deleteLocalUser(user: UserEntity) = userDao.eliminarUsuario(user)

    override suspend fun getUserById(id: Int): UserEntity? = userDao.obtenerUsuarioPorId(id)

    override suspend fun loginUser(user: String, pass: String): UserEntity? = userDao.login(user, pass)

    override suspend fun getRemoteUsers(): List<ApiUser> = apiService.getUsers()
}