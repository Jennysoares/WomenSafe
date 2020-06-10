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
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        pBar.visibility = GONE
        verifyLogin()
        entrarbt.setOnClickListener { login() }
        cadastrarbt.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        resetbt.setOnClickListener { startActivity(Intent(this, ResetPasswordActivity::class.java)) }
    }

    fun verifyLogin() {
        viewModel.verifyLogin {result ->
            if(result != null) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun login() {
        pBar.visibility = VISIBLE
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()

        viewModel.login(email, senha) { result, id ->
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            if(id == 1) {
                pBar.visibility = GONE
                finish()
            } else if(id == 2){
                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                pBar.visibility = GONE
                finish()
            }
        }
    }
}
