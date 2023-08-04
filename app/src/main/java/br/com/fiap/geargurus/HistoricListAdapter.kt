package br.com.fiap.geargurus

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.fiap.geargurus.model.Ride

class HistoricListAdapter(private val context: Activity, private val rides: ArrayList<Ride>)
        : ArrayAdapter<Ride>(context, R.layout.custom_historic_list, rides){

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_historic_list, null, true)

        val textId = rowView.findViewById(R.id.text_id) as TextView
        val textModal = rowView.findViewById(R.id.text_modal) as TextView
        val textDate = rowView.findViewById(R.id.text_date) as TextView
        val textDuration = rowView.findViewById(R.id.text_duration) as TextView

        textId.text = "Id: " + rides[position].id
        textDate.text = "Data: " + rides[position].date
        textModal.text = "Modal: " + rides[position].modal
        textDuration.text = "Duração: " + rides[position].duration

        return rowView
    }
}