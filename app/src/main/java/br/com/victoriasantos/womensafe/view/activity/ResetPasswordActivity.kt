package br.com.victoriasantos.womensafe.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.victoriasantos.womensafe.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetbt.setOnClickListener { alterarSenha() }
    }

    private fun alterarSenha(){
        if(emailet.text.toString() != ""){
            mAuth.sendPasswordResetEmail(emailet.text.toString())
            Toast.makeText(this, "E-mail para recuperar senha enviado", Toast.LENGTH_LONG).show()
            finish()
        }
        else{
            Toast.makeText(this, "Digite o E-mail", Toast.LENGTH_LONG).show()
            return
        }
    }

}
