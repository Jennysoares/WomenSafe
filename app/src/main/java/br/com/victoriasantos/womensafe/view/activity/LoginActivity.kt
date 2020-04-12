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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        entrarbt.setOnClickListener { login() }
        cadastrarbt.setOnClickListener { cadastro() }
        resetbt.setOnClickListener { resetarSenha() }

    }

    private fun login() {
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()

        viewModel.login(email, senha) { result, id ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            if(id == 1) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            else if(id == 2){
                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                finish()
            }

        }
    }


    private fun resetarSenha(){
        startActivity(Intent(this, ResetPasswordActivity::class.java))
        finish()
    }


    private fun cadastro() {
        startActivity(Intent(this, CadastroActivity::class.java))
        finish()
    }
}
