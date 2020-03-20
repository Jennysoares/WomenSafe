package br.com.victoriasantos.womensafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        cadastrobt.setOnClickListener { Cadastrar() }

    }

    private fun Cadastrar(){
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
                Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show()
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
