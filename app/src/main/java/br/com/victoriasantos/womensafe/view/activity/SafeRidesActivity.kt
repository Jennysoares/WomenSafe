package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.view.adapter.PlatesAdapter
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_safe_rides.*

class SafeRidesActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_rides)

        bt_avaliar_placa.setOnClickListener { startActivity(Intent(this,RegistrationPlateActivity::class.java)) }
        configureRecyclerView()
    }

    private fun configureRecyclerView(){
        recycleView_placas.layoutManager = LinearLayoutManager(this)
    }

}
