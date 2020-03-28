package br.com.victoriasantos.womensafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.emailet
import kotlinx.android.synthetic.main.activity_reset_password.resetbt

class ResetPasswordActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetbt.setOnClickListener { AlterarSenha() }
    }

    private fun AlterarSenha(){
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
