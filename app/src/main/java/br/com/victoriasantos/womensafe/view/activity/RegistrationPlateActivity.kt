package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_maps.view.*
import kotlinx.android.synthetic.main.activity_registration_plate.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.view.*
import kotlinx.android.synthetic.main.activity_reset_password.view.textView
import kotlinx.android.synthetic.main.item_evaluation_plates.*

class RegistrationPlateActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_plate)
        bt_confirmar.setOnClickListener { registerPlate() }
        bt_cancelar.setOnClickListener { finish() }

        val update = intent.getStringExtra("Update")
        if(update != null){
            val placa = intent.getStringExtra("placa")
            val coment = intent.getStringExtra("comentario")
            updatePlate(placa,coment)
        }
    }

    fun registerPlate(){
        val placa = numero_placa.text.toString().toUpperCase()
        val comentario = obs_avaliacao.text.toString()
        viewModel.registerPlate(null, placa, null, comentario, 1){ result ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()

            if(result.equals(getString(R.string.plate_registered_success))){
                finish()
            }
        }
    }

    fun updatePlate(placa : String, comentario : String){
        numero_placa.setText(placa)
        obs_avaliacao.setText(comentario)

        bt_confirmar.setOnClickListener {
            val placaUpdate = numero_placa.text.toString().toUpperCase()
            val comentarioUpdate = obs_avaliacao.text.toString()
            viewModel.registerPlate(placa, placaUpdate,comentario, comentarioUpdate, 2){ result ->
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()

                if(result.equals(getString(R.string.plate_updated_ok))){
                    finish()
                }
            }
        }


    }
}
