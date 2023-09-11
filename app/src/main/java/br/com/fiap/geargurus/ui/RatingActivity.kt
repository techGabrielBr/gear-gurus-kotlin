package br.com.fiap.geargurus.ui

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        val ratingBar : RatingBar = root.findViewById(R.id.ratingBar)
        val textViewRating : TextView = root.findViewById(R.id.textViewRating)
        val btnSave : Button = root.findViewById(R.id.btn_save)

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val ratingText = "Avaliação: $rating/5"
            textViewRating.text = ratingText
        }

        btnSave.setOnClickListener {
            this.finish()
            Toast.makeText(this, "Avaliação salva com sucesso   ", Toast.LENGTH_SHORT).show()
        }
    }
}