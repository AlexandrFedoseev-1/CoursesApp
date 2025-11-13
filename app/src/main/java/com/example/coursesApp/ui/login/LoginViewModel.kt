package com.example.coursesApp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    init {
        _isFormValid.value = false
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateForm(_email.value ?: "", _password.value ?: "")
    }
    fun onPasswordChanged(password: String) {
        _password.value = password
        validateForm(_email.value ?: "", _password.value ?: "")
    }

    private fun validateForm(email: String, password: String) {
        val isValid = isEmailValid(email) && password.isNotBlank()
        _isFormValid.value = isValid
    }

    private fun isEmailValid(email: String): Boolean {
        if (email.contains("а-яА-Я".toRegex())) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}