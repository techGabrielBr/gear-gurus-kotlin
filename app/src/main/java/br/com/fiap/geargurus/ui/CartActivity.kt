package br.com.fiap.geargurus.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
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