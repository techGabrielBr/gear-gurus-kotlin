package br.com.fiap.geargurus.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val goLogin : TextView = root.findViewById(R.id.go_login)
        val btnSignUp : Button = root.findViewById(R.id.sign_up)
        val textName : EditText = root.findViewById(R.id.name)
        val textEmail : EditText = root.findViewById(R.id.email)
        val textCpf : EditText = root.findViewById(R.id.cpf)
        val textPassword : EditText = root.findViewById(R.id.password)
        val textConfirm : EditText = root.findViewById(R.id.confirm)

        goLogin.setOnClickListener {
            startLogin()
        }

        btnSignUp.setOnClickListener {
            startLogin()
        }
    }

    private fun startLogin(){
        val i = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(i)
    }
}