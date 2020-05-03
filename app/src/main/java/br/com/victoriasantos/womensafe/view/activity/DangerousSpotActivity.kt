package br.com.victoriasantos.womensafe.view.activity


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_dangerous_spot.*


class DangerousSpotActivity : AppCompatActivity() {



    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangerous_spot)

        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")
        val endereco = intent.getStringExtra("endereco")
        endereco_lugar.text = endereco.toString()

        bt_cancelar.setOnClickListener { finish() }
        bt_confirmar.setOnClickListener { spotRegister(latitude, longitude) }

    }

    fun spotRegister(latitude : String, longitude : String){
        val comentario = comentario_lugar.text.toString()
            viewModel.spotRegister(latitude, longitude, comentario){ result, id ->
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if(id==1){
                    startActivity(Intent(this, MapsActivity::class.java))
                }
            }
    }
}