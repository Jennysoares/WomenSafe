package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class LoginActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        entrarbt.setOnClickListener { login() }
        cadastrarbt.setOnClickListener { cadastro() }
        resetbt.setOnClickListener { resetarSenha() }

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
                    val profiles = database.getReference("Usuários")

                    val query = profiles.orderByChild("email").equalTo(email)
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError){
                            Toast.makeText(this@LoginActivity, "Consulta Cancelada",Toast.LENGTH_LONG).show()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot != null && snapshot.hasChildren() == true) {// Verifica se possui dados{
                                profile = snapshot.children.first().getValue(Profile::class.java)
                                if (profile?.nomecompleto.toString().isNotEmpty() && profile?.telefone.toString().isNotEmpty() && profile?.username.toString().isNotEmpty()) {
                                    val intentMain = Intent(this@LoginActivity, ProfileActivity::class.java)
                                    startActivity(intentMain)
                                }
                                else {
                                    Toast.makeText(this@LoginActivity, "Preenchimento do perfil obrigatório!",Toast.LENGTH_LONG).show()
                                    val intentToProfile = Intent(this@LoginActivity, ProfileActivity::class.java)
                                    startActivity(intentToProfile)
                                }

                            }
                            else {
                                Toast.makeText(this@LoginActivity, "Preenchimento do perfil obrigatório!",Toast.LENGTH_LONG).show()
                                val intentToProfile = Intent(this@LoginActivity, ProfileActivity::class.java)
                                startActivity(intentToProfile)
                            }
                        }
                    })
            }
            else {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possivel entrar o aplicativo"
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun resetarSenha(){

        val intentToResetActivity = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intentToResetActivity)

    }


    private fun cadastro() {

        val intentCadastro = Intent(this, CadastroActivity::class.java)
        startActivity(intentCadastro)

    }
}
