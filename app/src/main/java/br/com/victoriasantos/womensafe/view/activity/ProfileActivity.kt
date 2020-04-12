package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Profile
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bt_cancelar.setOnClickListener{cancelar()}
        bt_excluirConta.setOnClickListener{excluirConta()}
        consulta()

    }

    private fun consulta() {

        viewModel.getEmail { email ->
            email_usuario.setText(email)
        }

        viewModel.consulta { perfil ->
            // Mostra os valores que estão no banco de dados
                nome_usuario.setText(perfil?.username)
                nomeCompleto_usuario.setText(perfil?.nomecompleto)
                telefone_usuario.setText(perfil?.telefone)
        }

        bt_confirmar.setOnClickListener { save() }

    }

    private fun save() {
        val emailCampo = email_usuario.text.toString()
        val nomecompleto = nomeCompleto_usuario.text.toString()
        val telefone = telefone_usuario.text.toString()
        val username = nome_usuario.text.toString()

        viewModel.saveData(emailCampo, nomecompleto, telefone, username){ result, id ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            if(id == 1){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun cancelar(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun excluirConta(){

            viewModel.deleteUser { result, id ->
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if(id == 1){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }


    }



}
