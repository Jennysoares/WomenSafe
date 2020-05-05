package br.com.victoriasantos.womensafe.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import kotlinx.android.synthetic.main.item_evaluations.view.*

class MarkerEvaluationsAdapter(private val context: Context, private val dataSet: Array<String>) : RecyclerView.Adapter<MarkerEvaluationsAdapter.MarkerEvaluationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerEvaluationsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_evaluations, parent, false)
        return MarkerEvaluationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MarkerEvaluationsViewHolder, position: Int) {
        val data = dataSet?.get(position)
        holder.comentario.text = data
    }

    class MarkerEvaluationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val comentario: TextView = itemView.comentario_avl
    }
}

