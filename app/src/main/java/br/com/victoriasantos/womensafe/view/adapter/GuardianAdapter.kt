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
import br.com.victoriasantos.womensafe.view.activity.GuardiansActivity
import kotlinx.android.synthetic.main.item_guardians.view.*

class GuardianAdapter(private val context : Context , private val activity: GuardiansActivity, private val dataSet: Array<Guardian>?) : RecyclerView.Adapter<GuardianAdapter.GuardianViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guardians, parent, false)
        return GuardianViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataSet != null) {
            return dataSet.size
        }
        else return 0
    }

    override fun onBindViewHolder(holder: GuardianViewHolder, position: Int) {
        val Guardian = dataSet?.get(position)
        holder.nome_guardian.text = "Nome: ${Guardian?.nome}"
        holder.email_guardian.text = Guardian?.email
        holder.telefone_guardian.text = "Telefone: ${Guardian?.telefone}"
        holder.excluir.setOnClickListener{
            activity.deleteGuardian(Guardian?.email)
        }
    }


    class GuardianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nome_guardian: TextView = itemView.nome_guardiao
        val email_guardian: TextView = itemView.email_guardiao
        val telefone_guardian: TextView = itemView.numero_guardiao
        val excluir : ImageView = itemView.delete
    }
}