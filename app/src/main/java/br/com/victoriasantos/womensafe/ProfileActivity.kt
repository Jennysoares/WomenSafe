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

        configurar()

    }

    private fun configurar()
    {
        val email = mAuth.currentUser?.email
        email_usuario.setText(email)

        bt_confirmar.setOnClickListener { save() }

        val profiles = database.getReference("profile")

        val query = profiles.orderByChild("email").equalTo(email)

        query.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError)
            {
                Toast.makeText(this@ProfileActivity,"Consulta Cancelada",Toast.LENGTH_LONG).show()

            }

            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot != null && snapshot.hasChildren() == true)
                {
                    profile = snapshot.children.first().getValue(Profile::class.java)

                    if(profile != null)
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
        profile = Profile(

            email = email_usuario.text.toString(),
            nomecompleto = nomeCompleto_usuario.text.toString(),
            telefone = telefone_usuario.text.toString(),
            username = nome_usuario.text.toString()

        )

        val uid = mAuth.currentUser?.uid

        if(uid != null){

            val userprofile = database.getReference("profile/$uid")

            if(nomeCompleto_usuario.text.toString().isNotEmpty() && telefone_usuario.text.toString().isNotEmpty() && nome_usuario.text.toString().isNotEmpty() ){
                userprofile.setValue(profile)
                val intentToMainActivity = Intent(this,MainActivity::class.java)
                startActivity(intentToMainActivity)
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

}
