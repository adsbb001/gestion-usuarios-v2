package com.cibertec.gestionusuariosv2.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(user: UserEntity)

    @Query("SELECT * FROM users_table")
    fun obtenerTodosLosUsuarios(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users_table WHERE usuario = :user AND contrasena = :pass LIMIT 1")
    fun login(user: String,pass: String): UserEntity?

    @Update
    suspend fun actualizarUsuario(user: UserEntity)

    @Delete
    suspend fun eliminarUsuario(user: UserEntity)

    @Query ("SELECT * FROM users_table WHERE id = :id")
    suspend fun obtenerUsuarioPorId(id: Int): UserEntity?

}