package br.com.victoriasantos.womensafe.interactor

import android.content.Context

import br.com.victoriasantos.womensafe.domain.Profile
import br.com.victoriasantos.womensafe.repository.FirebaseRepository


class FirebaseInterector(private val context: Context) {

    private val repository = FirebaseRepository(context)
    private var profile: Profile? = null

    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit) {


        if (email.isNullOrBlank()) {
            callback("EV")
            return
        }
        if (senha.isNullOrBlank()) {
            callback("SV")
            return
        } else {
            if (senha.length < 6) {
                callback("SC")
                return
            }
        }
        repository.cadastro(email, senha, callback)
    }

    fun login(email: String, senha: String, callback: (result: String) -> Unit) {


        if (email.isNullOrBlank()) {
            callback("EV")
            return
        }
        if (senha.isNullOrBlank()) {
            callback("SV")
            return
        } else {
            if (senha.length < 6) {
                callback("SC")
                return
            }
        }
        repository.login(email, senha) { result ->
            if (result == "S") {
                repository.consulta{ snapshot ->
                    if (snapshot != null && snapshot.hasChildren() == true) {// Verifica se possui dados{
                        profile = snapshot.children.first().getValue(Profile::class.java)
                        if (profile?.nomecompleto.toString().isNotEmpty() && profile?.telefone.toString().isNotEmpty() && profile?.username.toString().isNotEmpty()) {
                            callback("PP")
                        } else {
                            callback("PV")
                        }
                    } else {
                        callback("PV")
                    }
                }
            } else {
                callback(result)
            }
        }


    }

    fun getEmail(callback: (email: String) -> Unit){
        repository.getEmail {email ->
            if(email != null){
                callback(email)
            }
        }
    }

    fun consulta(callback: (perfil: Profile?) -> Unit) {
        repository.consulta { snapshot ->
            if (snapshot != null && snapshot.hasChildren() == true) {
                profile = snapshot.children.first().getValue(Profile::class.java)
                if (profile != null) {
                    callback(profile)
                } else {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }

    fun saveData(emailCampo: String, nomecompleto: String, telefone: String, username: String, callback: (result: String) -> Unit) {

            repository.getEmail { emailFinal ->
                if (!emailFinal.equals(emailCampo)) { // Pergunta se o email do usuario antigo(emailFinal) é diferente(por ter uma ! no início) ao email que está no campo da página(emailCampo)
                    val email = emailCampo
                    repository.UpdateEmail(email) { result ->
                        if (result == "S") {
                            if (nomecompleto.isNotEmpty() && telefone.isNotEmpty() && username.isNotEmpty()) {
                                repository.saveData(email, nomecompleto, telefone, username, callback)
                            } else {
                                callback("EMPTY DATA")
                                // Todos os campos devem ser preenchidos!"
                            }

                        }
                        else{
                            callback(result)
                        }

                    }
                }
                else{
                    val email = emailCampo
                    if (nomecompleto.isNotEmpty() && telefone.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()) {
                        repository.saveData(email, nomecompleto, telefone, username, callback)
                    } else {
                        callback("EMPTY DATA")
                        // Todos os campos devem ser preenchidos!"
                    }

                }
            }
    }

    fun deleteUser(callback: (result: String) -> Unit){
        repository.deleteUser(callback)
    }

    fun changePassword(email: String, callback: (result: String) -> Unit){
        if(email != ""){
            repository.changePassword(email)
            callback("EMAIL SENT")
        }
        else{
            callback("EMPTY EMAIL")
        }
    }
}



