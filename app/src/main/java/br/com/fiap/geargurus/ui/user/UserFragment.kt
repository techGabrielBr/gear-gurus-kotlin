package br.com.fiap.geargurus.ui.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.geargurus.ui.LoginActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.FragmentUserBinding
import br.com.fiap.geargurus.ui.ChangePasswordActivity
import br.com.fiap.geargurus.ui.PaymentMethodsActivity
import br.com.fiap.geargurus.ui.PlansActivity

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[UserViewModel::class.java]

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnLogOut : TextView = root.findViewById(R.id.log_out)

        val textUser : TextView = root.findViewById(R.id.text_user)

        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val userName = sharedPref?.getString("userName", "Teste")

        val btnGoPlans : TextView = root.findViewById(R.id.go_plans)

        val btnGoPaymentMethods : TextView = root.findViewById(R.id.go_payment_methods)

        val btnGoChangePassword : TextView = root.findViewById(R.id.go_change_password)

        textUser.text = "Olá, $userName"

        btnLogOut.setOnClickListener{
            logOut(sharedPref)
        }

        btnGoPlans.setOnClickListener {
            val i = Intent(this@UserFragment.context, PlansActivity::class.java)
            startActivity(i)
        }

        btnGoPaymentMethods.setOnClickListener {
            val i = Intent(this@UserFragment.context, PaymentMethodsActivity::class.java)
            startActivity(i)
        }

        btnGoChangePassword.setOnClickListener {
            val i = Intent(this@UserFragment.context, ChangePasswordActivity::class.java)
            startActivity(i)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logOut(sharedPref : SharedPreferences?){
        with(sharedPref?.edit()){
            this!!.remove("id")
            remove("userName")
            commit()
        }

        val i = Intent(this@UserFragment.context, LoginActivity::class.java)
        startActivity(i)
    }
}