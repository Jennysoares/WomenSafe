package br.com.victoriasantos.womensafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        entrarbt.setOnClickListener { login() }
        cadastrarbt.setOnClickListener { cadastro() }
        resetbt.setOnClickListener { ResetarSenha() }
    }

    private fun login(){
        val email = emailet.text.toString()
        val senha = senhaet.text.toString()

        if(email.isEmpty()) {
            Toast.makeText(this, "E-mail obrigatório", Toast.LENGTH_LONG).show()
            return
        }

        if(senha.isEmpty()){
            Toast.makeText(this, "Senha obrigatória", Toast.LENGTH_LONG).show()
            return
        }
        else{
            if(senha.length < 6){
                Toast.makeText(this, "Senha precisa ter ao menos 6 caracteres", Toast.LENGTH_LONG).show()
                return
            }
        }
        // validação no firebase, verificar se senha e email batem
        val operation = mAuth.signInWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)

            }
            else {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possivel entrar o aplicativo"
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun ResetarSenha(){

        if(emailet.text.toString() != ""){

            val operation = mAuth.sendPasswordResetEmail(emailet.text.toString())
            operation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "E-mail para recuperar senha enviado", Toast.LENGTH_LONG)
                        .show()

                } else {
                    val error = task.exception?.localizedMessage
                        ?: "Não foi possível enviar o email"
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            Toast.makeText(this, "E-mail vazio", Toast.LENGTH_LONG).show()
        }
    }

    private fun cadastro(){

        val intentCadastro = Intent(this, CadastroActivity::class.java)
        startActivity(intentCadastro)

    }


}
