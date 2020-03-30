package br.com.victoriasantos.womensafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bt_cancelar.setOnClickListener{cancelar()}
        bt_excluirConta.setOnClickListener{excluirConta()}
        consulta()

    }

    private fun consulta()
    {
        val email = mAuth.currentUser?.email
        email_usuario.setText(email)

        bt_confirmar.setOnClickListener { save() }

        //Daqui pra baixo é para buscar dados se o usuário já tiver algum cadastrado e mostrar nos campos
        val profiles = database.getReference("Usuários") //Define qual nó irá consultar

        val query = profiles.orderByChild("email").equalTo(email) //Procura qual os dados do usuário logado pelo email fornecido ao logar

        query.addListenerForSingleValueEvent(object : ValueEventListener
        { // Verifica se conseguiu fazer a consulta
            override fun onCancelled(p0: DatabaseError)
            {
                Toast.makeText(this@ProfileActivity,"Consulta Cancelada",Toast.LENGTH_LONG).show()

            }

            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot != null && snapshot.hasChildren() == true) // Verifica se possui dados
                {
                    profile = snapshot.children.first().getValue(Profile::class.java) //Pega os valores do primeiro que encontrar

                    if(profile != null) // Mostra os valores que estão no banco de dados
                    {
                        nome_usuario.setText(profile?.username)
                        nomeCompleto_usuario.setText(profile?.nomecompleto)
                        telefone_usuario.setText(profile?.telefone)

                    }
                }
            }
        } )

    }

    private fun save()
    {
        val usuario = mAuth.currentUser
        var emailFinal = mAuth.currentUser?.email
        val emailCampo = email_usuario.text.toString()

        // Verificação caso o usuário tenha alterado o campo email, e se sim irá atualizar o email antigo para o novo
        if(!emailFinal.equals(emailCampo)){ // Pergunta se o email do usuario antigo(emailFinal) é diferente(por ter uma ! no início) ao email que está no campo da página(emailCampo)
            emailFinal = emailCampo

            usuario?.updateEmail(emailFinal)?.addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    val error = task.exception?.localizedMessage
                        ?: "Houve um erro ao atualizar o e-mail!"
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            }
        }
        //Define quais são os dados de cada campo dos atributos
        profile = Profile(

            email = emailFinal,
            nomecompleto = nomeCompleto_usuario.text.toString(),
            telefone = telefone_usuario.text.toString(),
            username = nome_usuario.text.toString()

        )
        // Variável para encontrar o usuário logado
        val uid = mAuth.currentUser?.uid

        if(uid != null){
            // Variável que define qual nó será atualizado, nesse caso será o nó "Usuários"
            val userprofile = database.getReference("Usuários/$uid") // $uid é o onde será substituido pelo id do usuário logado

            if(nomeCompleto_usuario.text.toString().isNotEmpty() && telefone_usuario.text.toString().isNotEmpty() && nome_usuario.text.toString().isNotEmpty() ){ //Verificação para que nenhum campo esteja em branco
                userprofile.setValue(profile) //Atualiza/cria os dados
                val intentToMainActivity = Intent(this,MainActivity::class.java)
                startActivity(intentToMainActivity)
                finish()
            }
            else{
                Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show()
                return;
            }

        }
        else
        {
            Toast.makeText(this, "Não foi possível recuperar a chave do usuário",Toast.LENGTH_LONG).show()
        }

    }

    private fun cancelar(){
        var intentCancelar = Intent(this, LoginActivity::class.java)
        startActivity(intentCancelar)
        finish()

    }

    private fun excluirConta(){

        var flagUsuario = true
        var flagDados = true

        //exclui a conta na autenticação
        val usuario = mAuth.currentUser

        usuario?.delete()?.addOnCompleteListener { task ->
            if(!task.isSuccessful){
                flagUsuario = false
            }
        }

        //exclui os dados do usuario do banco de dados
        val uid = usuario?.uid
        val dados = database.getReference("Usuários/$uid")

        dados.removeValue().addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                flagDados = false
            }
        }

        // Verifica se a exclusão deu certo ou não
        if (flagDados == true && flagUsuario == true){
            Toast.makeText(this, "Usuário excluido com sucesso!", Toast.LENGTH_LONG).show()
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
            finish()
        }
        else{
            if(flagUsuario == false){
                Toast.makeText(this, "Não foi possível excluir este usuário!", Toast.LENGTH_LONG).show()
            }
            if(flagDados == false){
                Toast.makeText(this, "Não foi possível excluir os dados!", Toast.LENGTH_LONG).show()
            }
        }
    }



}
