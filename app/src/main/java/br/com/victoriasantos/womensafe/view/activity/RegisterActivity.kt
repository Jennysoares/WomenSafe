package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        pBar.visibility = GONE

        cadastrobt.setOnClickListener { cadastrar() }

    }

    private fun cadastrar(){
        pBar.visibility = VISIBLE
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()
        val confirmacao = senhaet2.text.toString()

        viewModel.cadastro(email, senha, confirmacao) { result, id ->
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if(id == 1) {
                    startActivity(Intent(this@RegisterActivity, ProfileActivity::class.java))
                    pBar.visibility = GONE
                    finish()
                }
            pBar.visibility = GONE
        }

    }
}