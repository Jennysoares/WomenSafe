package br.com.victoriasantos.womensafe.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.LocationData
import br.com.victoriasantos.womensafe.view.activity.ContributionsActivity
import br.com.victoriasantos.womensafe.view.activity.MapsActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.item_evaluation_plates.view.*

class SpotEvaluationAdapter(
    private val contributionsActivity: ContributionsActivity,
    private val dataSet: Array<LocationData>?,
    private val mostrar: Int
) : RecyclerView.Adapter<SpotEvaluationAdapter.SpotEvaluationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotEvaluationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evaluation_plates, parent, false)
        return SpotEvaluationViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dataSet != null) {
            return dataSet.size
        } else return 0
    }

    override fun onBindViewHolder(holder: SpotEvaluationViewHolder, position: Int) {
        val LocationData = dataSet?.get(position)
        if (mostrar == 1) {
            val endereco = contributionsActivity.getAddress(
                LatLng(
                    LocationData!!.latitude,
                    LocationData.longitude
                )
            )
            if (endereco.isNullOrBlank()) {
                holder.endereco.text = contributionsActivity.getString(R.string.address_unavailable)
            } else {
                holder.endereco.text = endereco
            }
            holder.comentario.text = LocationData?.evaluation
            holder.data.text = LocationData?.data
            holder.excluir.setOnClickListener {
                contributionsActivity.deleteSpotEvaluation(
                    LocationData.latitude,
                    LocationData.longitude,
                    LocationData.evaluation
                )
            }
            holder.edit.setOnClickListener {
                contributionsActivity.updateSpot(endereco!!, LocationData?.evaluation!!, LocationData?.data!!,LocationData.latitude.toString(),
                    LocationData.longitude.toString())
            }
        } else {
            holder.endereco.visibility = View.INVISIBLE
            holder.excluir.visibility = View.INVISIBLE
            holder.edit.visibility = View.INVISIBLE
            holder.data.text = LocationData?.data
            holder.comentario.text = LocationData?.evaluation
        }
    }

    class SpotEvaluationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val endereco: TextView = itemView.texto_id
        val comentario: TextView = itemView.comentario_avl
        val excluir: ImageView = itemView.delete
        val edit: ImageView = itemView.edit
        val data: TextView = itemView.data_registro
    }
}