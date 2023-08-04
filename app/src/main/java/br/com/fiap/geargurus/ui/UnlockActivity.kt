package br.com.fiap.geargurus.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityUnlockBinding

class UnlockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUnlockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root
        val goNextBtn = root.findViewById<TextView>(R.id.go_next)
        val goBackBtn = root.findViewById<TextView>(R.id.go_back)

        goBackBtn.setOnClickListener {
            val i = Intent(this@UnlockActivity, MainActivity::class.java)
            startActivity(i)
        }

        goNextBtn.setOnClickListener {
            val i = Intent(this@UnlockActivity, MainActivity::class.java)
            i.putExtra("ShowModal", "true")
            startActivity(i)
        }
    }
}