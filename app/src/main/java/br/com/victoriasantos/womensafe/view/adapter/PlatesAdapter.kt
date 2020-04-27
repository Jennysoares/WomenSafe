package br.com.victoriasantos.womensafe.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Plate
import kotlinx.android.synthetic.main.item_plates.view.*

class PlatesAdapter(private val dataSet: Array<Plate>) : RecyclerView.Adapter<PlatesAdapter.PlatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plates, parent, false)
        return PlatesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: PlatesViewHolder, position: Int) {
        val Plate = dataSet[position]
        holder.placa.text = Plate.placa
        holder.comentario.text = Plate.comentario

    }

    class PlatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val placa: TextView = itemView.placa
        val comentario: TextView = itemView.comentario

    }
}