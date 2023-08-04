package br.com.fiap.geargurus.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityPaymentMethodsBinding

class PaymentMethodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val btnCart : ImageView = root.findViewById(R.id.go_cart)

        btnCart.setOnClickListener {
            val i = Intent(this@PaymentMethodsActivity, CartActivity::class.java)
            startActivity(i)
        }

        val btnBack : ImageView = root.findViewById(R.id.go_back)

        btnBack.setOnClickListener {
            this.finish()
        }
    }
}