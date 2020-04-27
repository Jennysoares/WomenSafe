package br.com.victoriasantos.womensafe.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import kotlinx.android.synthetic.main.item_guardians.view.*
import kotlinx.android.synthetic.main.item_guardians.view.email_guardiao
import kotlinx.android.synthetic.main.item_guardians.view.nome_guardiao
import br.com.victoriasantos.womensafe.view.activity.GuardiansActivity

class GuardianAdapter(private val dataSet: Array<Guardian>) : RecyclerView.Adapter<GuardianAdapter.GuardianViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guardians, parent, false)
        return GuardianViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: GuardianViewHolder, position: Int) {
        val Guardian = dataSet[position]
        holder.nome_guardian.text = "Nome: ${Guardian.nome}"
        holder.email_guardian.text = Guardian.email
        holder.telefone_guardian.text = "Telefone: ${Guardian.telefone}"
        holder.excluir.setOnClickListener {

        }
    }

    class GuardianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nome_guardian: TextView = itemView.nome_guardiao
        val email_guardian: TextView = itemView.email_guardiao
        val telefone_guardian: TextView = itemView.numero_guardiao
        val excluir : ImageView = itemView.delete

    }
}