package br.com.victoriasantos.womensafe.repository

import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.victoriasantos.womensafe.view.activity.ProfileActivity
import com.google.firebase.auth.FirebaseAuth


class FirebaseRepository (context: Context) {
    private val mAuth = FirebaseAuth.getInstance()

    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit){
        val operation = mAuth.createUserWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback("S")
            }
            else{
                val error = task.exception?.localizedMessage
                    ?: "Não foi possível entrar no aplicativo no momento"
                    callback(error)

            }
        }
    }
}