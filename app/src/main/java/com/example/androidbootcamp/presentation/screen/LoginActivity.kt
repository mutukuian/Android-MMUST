package com.example.androidbootcamp.presentation.screen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidbootcamp.R
import com.example.androidbootcamp.common.Resource
import com.example.androidbootcamp.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Assuming you have two EditText views for email and password, and two buttons for login and register
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.loginUser(email, password)
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.registerUser(email, password)
        }

        // Observe the login result
        lifecycleScope.launch {
            viewModel.loginResult.collect { result ->
                // Handle the result, update UI accordingly
                when (result) {
                    is Resource.Success -> {
                        // Login successful, navigate to the next screen or perform other actions
                        Toast.makeText(this@LoginActivity, result.message ?: "Login success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        // Login failed, show an error message to the user
                        Toast.makeText(this@LoginActivity, result.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        // Show loading indicator if needed
                        Toast.makeText(this@LoginActivity, result.message ?: "Validating credentials... Please wait", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
