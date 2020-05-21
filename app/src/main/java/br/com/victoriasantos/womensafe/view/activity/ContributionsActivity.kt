package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.view.adapter.PlatesAdapter
import br.com.victoriasantos.womensafe.view.adapter.SpotEvaluationAdapter
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_contributions.*
import java.io.IOException

class ContributionsActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contributions)
        pBar.visibility = GONE
        pBar2.visibility = GONE

        configureRecyclerView()
        showPlatesAndSpotsContribution()
    }

    private fun configureRecyclerView(){
        recycleview_placa.layoutManager = LinearLayoutManager(this)
        recycleview_lugares_perigosos.layoutManager = LinearLayoutManager(this)
    }

    fun showPlatesAndSpotsContribution(){
        pBar.visibility = VISIBLE
        viewModel.showPlate(1, null){ plates ->
            val adapter = PlatesAdapter(this, plates,1)
            recycleview_placa.adapter = adapter
            pBar.visibility = GONE
        }
        pBar2.visibility = VISIBLE
        viewModel.showSpotEvaluation{ spots ->
            val adapter = SpotEvaluationAdapter(this, spots,1)
            recycleview_lugares_perigosos.adapter = adapter
            pBar.visibility = GONE
        }

    }

    fun deletePlate(placa: String?, comentario: String? ){
        pBar.visibility = VISIBLE
        viewModel.deletePlate(placa, comentario){ result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
            pBar.visibility = GONE
        }
    }

    fun deleteSpotEvaluation(latitude: Double?, longitude: Double?,evaluation: String? ){
        pBar2.visibility = VISIBLE
        viewModel.deleteSpotEvaluation(latitude, longitude, evaluation){ result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
            pBar2.visibility = GONE
        }
    }

    fun updatePlate(placa : String, comentario : String){
        val intent = Intent(this, RegistrationPlateActivity::class.java)
        intent.putExtra("placa", placa)
        intent.putExtra("comentario",comentario)
        intent.putExtra("Update", "s")
        startActivity(intent)
    }

    fun updateSpot(endereco: String, comentario: String, data: String, latitude: String?, longitude: String?){
        val intent = Intent(this, DangerousSpotActivity::class.java)
        intent.putExtra("endereco", endereco)
        intent.putExtra("avaliacao",comentario)
        intent.putExtra("data",data)
        intent.putExtra("latitude",latitude)
        intent.putExtra("longitude",longitude)
        intent.putExtra("Update", "s")
        startActivity(intent)
    }

    fun getAddress(latLng: LatLng): String? {

        val geocoder = Geocoder(this)
        var addresses: List<Address>? = null
        var Address1: String? = null

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        if (addresses != null) {
            Address1 = addresses[0].getAddressLine(0)
        }

        return Address1
    }



}
