package com.kiren.mob21firebase.ui.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.kiren.mob21firebase.core.service.AuthService
import com.kiren.mob21firebase.data.model.User
import com.kiren.mob21firebase.data.repo.UserRepo
import com.kiren.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    fun register(email: String, pass:String, pass2:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val error = validate(email, pass, pass2)
            if(!error.isNullOrEmpty()) {
                _error.emit(error)
            } else {
                val res = safeApiCall { authService.register(email, pass) }
                if( res == null) {
                    _error.emit("Register failed")
                } else {
                   userRepo.addUser(
                       res.uid,
                       User(name = "kiren", email = res.email?: "")
                   )
                    _success.emit("Register successful")
                }
            }
        }
    }

    fun validate(email: String, pass: String, pass2: String): String? {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            "Please Enter a valid email"
        } else if(pass.length < 6) {
            "Password length should be greater than 5"
        } else if (pass != pass2) {
            "Confirm Password is not the same as Password"
        } else {
            null
        }
    }
}