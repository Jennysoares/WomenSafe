package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetbt.setOnClickListener { alterarSenha() }
    }

    private fun alterarSenha(){
        val email = emailet.text.toString()
        viewModel.changePassword(email) { result, id ->

            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            if(id == 1){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

    }
}
