package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.victoriasantos.womensafe.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        cadastrobt.setOnClickListener { cadastrar() }

    }

    private fun cadastrar(){
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "Email obrigatório", Toast.LENGTH_LONG).show()
            return
        }

        if(senha.isEmpty()){
            Toast.makeText(this, "Senha obrigatória", Toast.LENGTH_LONG).show()
            return
        }
        else{
            if(senha.length<6){
                Toast.makeText(this, "Senha precisa ter ao menos 6 caracteres", Toast.LENGTH_LONG).show()
                return
            }
        }

        val operation = mAuth.createUserWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Email autenticado, para concluir seu cadastro preencha o perfil", Toast.LENGTH_LONG).show()
                val intentToProfile = Intent(this, ProfileActivity::class.java)
                startActivity(intentToProfile)
                finish()
            }
            else{
                val error = task.exception?.localizedMessage
                    ?: "Não foi possível entrar no aplicativo no momento"
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }


    }

}