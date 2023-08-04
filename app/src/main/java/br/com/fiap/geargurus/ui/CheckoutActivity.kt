package br.com.fiap.geargurus.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val btnBack : Button = root.findViewById(R.id.btn_back)

        val extras = this.intent.extras

        val selected = extras?.getString("Plan")?.split(":")

        val textPlan = root.findViewById<TextView>(R.id.plan)
        val textPrice = root.findViewById<TextView>(R.id.price)

        textPlan.text = "Plano: " + selected?.get(0) ?: "Plano: A"
        textPrice.text = "Preço: " + selected?.get(1) ?: "Preço: 20,00"

        btnBack.setOnClickListener {
            this.finish()
        }

        val btnCart : ImageView = root.findViewById(R.id.go_cart)

        btnCart.setOnClickListener {
            val i = Intent(this@CheckoutActivity, CartActivity::class.java)
            startActivity(i)
        }
    }
}