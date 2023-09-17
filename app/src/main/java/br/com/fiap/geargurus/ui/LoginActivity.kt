package br.com.fiap.geargurus.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private var _authFails = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val goSignUp : TextView = root.findViewById(R.id.go_sign_up)
        val btnLogin : Button = root.findViewById(R.id.btn_login)
        val textEmail : EditText = root.findViewById(R.id.email)
        val textPassword : EditText = root.findViewById(R.id.password)
        val textAuthError : TextView = root.findViewById(R.id.text_auth_error)
        val textBlockedAccount : TextView = root.findViewById(R.id.text_blocked_account)

        goSignUp.setOnClickListener{
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
        }

        btnLogin.setOnClickListener {
            if((textEmail.text.trim().toString() == "email@email.com") && (textPassword.text.trim().toString() == "Teste23")){
                textAuthError.visibility = View.GONE

                if(_authFails < 7){
                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                }

            }else{
                _authFails += 1

                if(_authFails >= 7){
                    //block account (brute force suspect)
                    textAuthError.visibility = View.GONE
                    textBlockedAccount.visibility = View.VISIBLE
                }else{
                    //show message
                    textAuthError.visibility = View.VISIBLE
                }
            }
        }
    }
}