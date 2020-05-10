package br.com.victoriasantos.womensafe.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Plate
import br.com.victoriasantos.womensafe.view.activity.ContributionsActivity
import kotlinx.android.synthetic.main.item_evaluation_plates.view.*

class PlatesAdapter(private val contributionsActivity: ContributionsActivity, private val dataSet: Array<Plate>?, private val mostrar: Int) : RecyclerView.Adapter<PlatesAdapter.PlatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evaluation_plates, parent, false)
        return PlatesViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataSet != null) {
            return dataSet.size
        }
        else return 0
    }

    override fun onBindViewHolder(holder: PlatesViewHolder, position: Int) {
        val Plate = dataSet?.get(position)
        holder.placa.text = Plate?.placa
        holder.comentario.text = Plate?.comentario
        if(mostrar == 1){
            holder.excluir.setOnClickListener{
                contributionsActivity.deletePlate(Plate?.placa, Plate?.comentario)
            }
        } else{
            holder.excluir.visibility = View.INVISIBLE
        }
    }

    class PlatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val placa: TextView = itemView.texto_id
        val comentario: TextView = itemView.comentario_avl
        val excluir : ImageView = itemView.delete
    }
}