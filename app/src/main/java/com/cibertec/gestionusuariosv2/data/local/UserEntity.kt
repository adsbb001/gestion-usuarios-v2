package com.cibertec.gestionusuariosv2.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey (autoGenerate = true) val id: Int=0,
    val nombreCompleto: String ="",
    val usuario: String,
    val correo: String,
    val contrasena: String

)


