package br.com.victoriasantos.womensafe.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.victoriasantos.womensafe.R
import kotlinx.android.synthetic.main.activity_dangerous_spot.*


class DangerousSpotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangerous_spot)

        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")
        val endereco = intent.getStringExtra("endereco")
        endereco_lugar.text = endereco.toString()

        bt_cancelar.setOnClickListener { finish() }
        bt_confirmar.setOnClickListener { cadastrarLocalPerigoso(latitude, longitude) }

    }

    fun cadastrarLocalPerigoso(latitude : String?, longitude : String?){

    }
}