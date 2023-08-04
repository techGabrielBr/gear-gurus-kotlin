package br.com.fiap.geargurus.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityPlansBinding

class PlansActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlansBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root

        var selectedPlan = "A:20,00"

        val btnBack : Button = root.findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            this.finish()
        }

        val planA: LinearLayout = root.findViewById(R.id.plan_a)

        planA.setBackgroundDrawable(ColorDrawable(Color.parseColor("#139DF1")))

        val planB: LinearLayout = root.findViewById(R.id.plan_b)
        val planC: LinearLayout = root.findViewById(R.id.plan_c)

        val plans = arrayListOf<LinearLayout>(planA, planB, planC)

        plans.forEach { p -> p.setOnClickListener{
            resetColor(plans)
            it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#139DF1")))

            if(p == plans[0]){
                selectedPlan = "A:20,00"
            }else if(p == plans[1]){
                selectedPlan = "B:30,00"
            }else{
                selectedPlan = "C:40,00"
            }
        } }

        val btnCheckout : Button = root.findViewById(R.id.btn_checkout)

        btnCheckout.setOnClickListener {
            val i = Intent(this@PlansActivity, CheckoutActivity::class.java)
            i.putExtra("Plan", selectedPlan)
            startActivity(i)
        }
    }

    private fun resetColor(plans: ArrayList<LinearLayout>){
        plans.forEach {it.setBackgroundDrawable(ColorDrawable(Color.parseColor("#EAE8E8")))}
    }
}