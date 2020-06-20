package br.com.victoriasantos.womensafe.view.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.view.activity.GuardiansActivity
import kotlinx.android.synthetic.main.item_guardians.view.*

class GuardianAdapter(private val context : Context , private val activity: GuardiansActivity, private val dataSet: Array<Guardian>?) : RecyclerView.Adapter<GuardianAdapter.GuardianViewHolder>() {
   private val REQUEST_PHONE_CALL = 1

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
        holder.nome_guardian.text = context.getString(R.string.nome_titulo) + Guardian?.nome
        holder.email_guardian.text = Guardian?.email
        holder.telefone_guardian.text = context.getString(R.string.telefone_titulo) + Guardian?.telefone
        holder.excluir.setOnClickListener{
            activity.deleteGuardian(Guardian?.email)
        }
        holder.ligar.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else{
                activity.callGuardian(Guardian?.telefone)
            }
        }
    }

    class GuardianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nome_guardian: TextView = itemView.nome_guardiao
        val email_guardian: TextView = itemView.email_guardiao
        val telefone_guardian: TextView = itemView.numero_guardiao
        val excluir : ImageView = itemView.delete
        val ligar: ImageView = itemView.call
    }
}