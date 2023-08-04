package br.com.fiap.geargurus.ui.historic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.geargurus.HistoricListAdapter
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.FragmentHistoricBinding
import br.com.fiap.geargurus.model.Ride

class HistoricFragment : Fragment() {

    private var _binding: FragmentHistoricBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[HistoricViewModel::class.java]

        _binding = FragmentHistoricBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val userId = sharedPref?.getInt("id", -1)

        val listView : ListView = root.findViewById(R.id.list_view)

        val list = userId?.let { getRides(it) }

        listView.adapter = activity?.let { list?.let { l -> HistoricListAdapter(it, l) } }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRides(_id : Int) : ArrayList<Ride>{
        val r1 = Ride("FDE2341SDE", "21/07/23", "Bicicleta", "1h 20min")
        val r2 = Ride("KTE5441SSD", "18/07/23", "Patinete", "20min")
        val r3 = Ride("AVR2341SRE", "17/07/23", "Patinete", "40min")
        val r4 = Ride("FDT5441SDD", "01/07/23", "Bicicleta", "1h 50min")

        return arrayListOf(r1, r2, r3, r4)
    }
}