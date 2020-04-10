package br.com.victoriasantos.womensafe.viewmodel

import android.app.Application
import android.app.SharedElementCallback
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import br.com.victoriasantos.womensafe.interactor.FirebaseInterector
import br.com.victoriasantos.womensafe.view.activity.ProfileActivity

class FirebaseViewModel (val app: Application) : AndroidViewModel(app) {
    private val interactor = FirebaseInterector(app.applicationContext)

    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit){
        interactor.cadastro(email, senha){ result ->
            if(result == "EV"){
                callback("Espaços em branco não são permitidos")
            }
            else if(result == "EN"){
                callback("Email obrigatório")
            }
            else if(result == "SV") {
               callback("Espaços em branco não são permitidos")
            }
            else if(result == "SN"){
               callback("Senha obrigatória")
            }
            else if(result == "SC"){
                callback("Senha precisa ter ao menos 6 caracteres")
            }
            else if(result == "S"){
                callback("Usuário cadastrado com sucesso")
            }
            else{
                callback(result)

            }
        }
    }


}