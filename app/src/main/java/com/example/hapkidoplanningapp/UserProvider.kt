package com.example.hapkidoplanningapp

import com.example.hapkidoplanningapp.domain.User

interface UserProvider {
    fun getUser(): User?

    fun updateUser(user: User?)
}