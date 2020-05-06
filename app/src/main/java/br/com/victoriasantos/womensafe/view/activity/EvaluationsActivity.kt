package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.R.layout.activity_evaluations
import br.com.victoriasantos.womensafe.view.adapter.MarkerEvaluationsAdapter
import br.com.victoriasantos.womensafe.view.adapter.SpotEvaluationAdapter
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_evaluations.*

class EvaluationsActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_evaluations)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val address = intent.getStringExtra("endereço")

        if(!address.isNullOrBlank()){
            address_field.text = address
        }
        else{
            address_field.text = getString(R.string.address_unavailable)
        }
        configureRecyclerView()
        showEvaluations(latitude, longitude)
        bt_cadastrarAvaliacao.setOnClickListener {
            val intent = Intent(this, DangerousSpotActivity::class.java)
            intent.putExtra("latitude", latitude.toString())
            intent.putExtra("longitude",longitude.toString())
            intent.putExtra("endereco", address)
            startActivity(intent)
        }
    }

    fun configureRecyclerView(){
        evaluationRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun showEvaluations(latitude: Double, longitude: Double){
        viewModel.showEvaluations(latitude, longitude){ result ->
            if(result.isNullOrEmpty()){
                Toast.makeText(this, "Não foi possível recuperar as avaliações", Toast.LENGTH_LONG).show()
            }
            else{
                val adapter = MarkerEvaluationsAdapter(this, result)
                evaluationRecyclerView.adapter = adapter
            }

        }
    }
}

