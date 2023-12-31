package com.kiren.mob21firebase.data.repo

import com.kiren.mob21firebase.data.model.User

interface UserRepo {

     suspend fun addUser(id: String, user: User)

     suspend fun getUser(id: String): User?

     suspend fun updateUser(id: String, user: User)
}