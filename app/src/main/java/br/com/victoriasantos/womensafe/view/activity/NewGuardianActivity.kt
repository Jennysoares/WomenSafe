package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_new_guardian.*
import kotlinx.android.synthetic.main.activity_new_guardian.bt_confirmar
import kotlinx.android.synthetic.main.activity_profile.*

class NewGuardianActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_guardian)

        bt_confirmar.setOnClickListener {
            registerGuardian()

        }

        bt_cancelar.setOnClickListener{
            finish()
        }
    }

    fun registerGuardian(){
        val nome = nome_guardiao.text.toString()
        val telefone = telefone_guardiao.text.toString()
        val email = email_guardiao.text.toString()

        viewModel.registerGuardian(nome, telefone, email){ result ->

            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            finish()

        }


    }
}
