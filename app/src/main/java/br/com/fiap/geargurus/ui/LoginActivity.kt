package br.com.fiap.geargurus.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityLoginBinding
import br.com.fiap.geargurus.model.User

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val goSignUp : TextView = root.findViewById(R.id.go_sign_up)
        val btnLogin : Button = root.findViewById(R.id.btn_login)
        val textEmail : EditText = root.findViewById(R.id.email)
        val textPassword : EditText = root.findViewById(R.id.password)

        goSignUp.setOnClickListener{
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }
    }
}