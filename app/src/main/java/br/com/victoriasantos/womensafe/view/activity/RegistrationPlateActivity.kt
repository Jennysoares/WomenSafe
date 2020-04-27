package br.com.victoriasantos.womensafe.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_registration_plate.*

class RegistrationPlateActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_plate)

        bt_confirmar.setOnClickListener { registerPlate() }
        bt_cancelar.setOnClickListener { finish() }
    }

    fun registerPlate(){
        val placa = numero_placa.text.toString()
        val comentario = obs_avaliacao.text.toString()
        viewModel.registerPlate(placa, comentario){ result ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()

            if(result.equals(getString(R.string.plate_registered_success))){
                finish()
            }
        }
    }
}
