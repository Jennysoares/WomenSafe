package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
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
        pBar.visibility = GONE

        bt_avaliar_placa.setOnClickListener { startActivity(Intent(this,RegistrationPlateActivity::class.java)) }
        bt_pesquisa.setOnClickListener { searchPlate() }
        configureRecyclerView()
        showPlates()
    }

    private fun configureRecyclerView(){
        recycleView_placas.layoutManager = LinearLayoutManager(this)
    }

    fun showPlates(){
        pBar.visibility = VISIBLE
        viewModel.showPlate(2, null){ plates ->
            val adapter = PlatesAdapter(ContributionsActivity(), plates, 0)
            recycleView_placas.adapter = adapter
            pBar.visibility = GONE
        }
    }

    fun searchPlate(){
        pBar.visibility = VISIBLE
        val placa = pesquisa.text.toString().toUpperCase()

        if(placa.isNullOrBlank()){
            showPlates()
        } else{
            viewModel.showPlate(3, placa){ plates ->
                if(plates.isNullOrEmpty()){
                    pBar.visibility = GONE
                    Toast.makeText(this, getString(R.string.search_empty_plates), Toast.LENGTH_LONG).show()
                } else{
                    val adapter = PlatesAdapter(ContributionsActivity(), plates, 0)
                    recycleView_placas.adapter = adapter
                    pBar.visibility = GONE
                }
            }
        }
    }
}
