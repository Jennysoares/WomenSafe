package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_cadastro.*

class RegistrerActivity : AppCompatActivity() {
    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        cadastrobt.setOnClickListener { cadastrar() }

    }

    private fun cadastrar(){
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()

        viewModel.cadastro(email, senha) { result, id ->

                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if(id == 1) {
                    startActivity(Intent(this@RegistrerActivity, ProfileActivity::class.java))
                    finish()
                }
        }

    }
}