package br.com.victoriasantos.womensafe.view.activity


import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_dangerous_spot.*


class DangerousSpotActivity : AppCompatActivity() {


    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangerous_spot)
        pBar.visibility = GONE

        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")
        val endereco = intent.getStringExtra("endereco")

        if (!endereco.isNullOrBlank()) {
            endereco_lugar.text = endereco.toString()
        } else {
            endereco_lugar.text = getString(R.string.address_unavailable)
        }

        bt_cancelar.setOnClickListener { finish() }
        bt_confirmar.setOnClickListener { spotRegister(latitude, longitude) }

        val update = intent.getStringExtra("Update")
        if (update != null) {
            val end = intent.getStringExtra("endereco")
            val aval = intent.getStringExtra("avaliacao")
            val data = intent.getStringExtra("data")
            val lat = intent.getStringExtra("latitude")
            val long = intent.getStringExtra("longitude")
            spotUpdate(end, aval, data, lat, long)
        }
    }

    fun spotRegister(latitude: String, longitude: String) {
        pBar.visibility = VISIBLE
        val comentario = comentario_lugar.text.toString()
        viewModel.spotRegister(latitude, longitude, null, comentario, null, 1) { result, id ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            pBar.visibility = GONE
            if (id == 1) {
                finish()
            }
        }
    }

    fun spotUpdate(endereco: String, avaliacao: String, data: String, latitude: String?, longitude: String?) {
        endereco_lugar.setText(endereco)
        comentario_lugar.setText(avaliacao)
        bt_confirmar.setOnClickListener {

            val comentarioUpdate = comentario_lugar.text.toString()
            viewModel.spotRegister(latitude, longitude, avaliacao, comentarioUpdate, data, 2) { result, id ->
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if (id == 1) {
                    finish()
                }
            }
        }
    }
}