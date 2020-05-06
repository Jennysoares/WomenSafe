package br.com.victoriasantos.womensafe.view.activity

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        configureRecyclerView()
        showPlatesAndSpotsContribution()
    }

    private fun configureRecyclerView(){
        recycleview_placa.layoutManager = LinearLayoutManager(this)
        recycleview_lugares_perigosos.layoutManager = LinearLayoutManager(this)
    }

    fun showPlatesAndSpotsContribution(){
        viewModel.showPlate{ plates ->
            val adapter = PlatesAdapter(this, plates)
            recycleview_placa.adapter = adapter
        }

        viewModel.showSpotEvaluation{ spots ->
            val adapter = SpotEvaluationAdapter(this, spots)
            recycleview_lugares_perigosos.adapter = adapter
        }

    }

    fun deletePlate(placa: String?, comentario: String? ){
        viewModel.deletePlate(placa, comentario){ result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
        }
    }

    fun deleteSpotEvaluation(latitude: Double?, longitude: Double?,evaluation: String? ){
        viewModel.deleteSpotEvaluation(latitude, longitude, evaluation){ result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
        }
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
