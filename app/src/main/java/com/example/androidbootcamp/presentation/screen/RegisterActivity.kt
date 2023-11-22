package com.example.androidbootcamp.presentation.screen

import android.annotation.SuppressLint
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
class RegisterActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Assuming you have two EditText views for email and password, and a button for registration
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.registerUser(email, password)
        }

        // Observe the registration result
        lifecycleScope.launch {
            viewModel.registrationResult.collect { result ->
                // Handle the result, update UI accordingly
                when (result) {
                    is Resource.Success -> {
                        // Registration successful, navigate to the next screen or perform other actions
                        Toast.makeText(this@RegisterActivity, result.message ?: "Registration Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        // Registration failed, show an error message to the user
                        Toast.makeText(this@RegisterActivity, result.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        // Show loading indicator if needed
                        Toast.makeText(this@RegisterActivity, result.message ?: "Registration in process...Please wait", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
