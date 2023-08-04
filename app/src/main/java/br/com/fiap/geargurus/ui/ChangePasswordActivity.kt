package br.com.fiap.geargurus.ui

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val btnBack : Button = root.findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            this.finish()
        }

        val btnSave : Button = root.findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            this.finish()
        }
    }
}