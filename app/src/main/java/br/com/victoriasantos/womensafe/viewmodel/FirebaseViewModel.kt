package br.com.victoriasantos.womensafe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.domain.Profile
import br.com.victoriasantos.womensafe.interactor.FirebaseInterector

class FirebaseViewModel (val app: Application) : AndroidViewModel(app) {
    private val interactor = FirebaseInterector(app.applicationContext)

    fun cadastro(email: String, senha: String, callback: (result: String, id: Int) -> Unit){
        interactor.cadastro(email, senha){ result ->

            if(result == "EV"){
                callback(app.applicationContext.getString(R.string.email_required), 0)
            }
            else if(result == "SV"){
               callback(app.applicationContext.getString(R.string.password_required), 0)
            }
            else if(result == "SC"){
                // TODO: MUDAR AS STRINGS PARA O PADRAO ACIMA
                callback(app.applicationContext.getString(R.string.short_password), 0)
            }
            else if(result == "S"){
                callback(app.applicationContext.getString(R.string.auth_email), 1)
            }
            else{
                callback(result, 0)
            }
        }
    }

    fun login(email: String, senha: String, callback: (result: String, id: Int) -> Unit) {

        interactor.login(email, senha){ result ->

           if(result == "EV"){
                callback("Email obrigatório", 0)
            }
            else if(result == "SV"){
                callback("Senha obrigatória", 0)
            }
            else if(result == "SC"){
                callback("Senha precisa ter ao menos 6 caracteres", 0)
            }
            else if(result == "S"){
                callback("Email autenticado, para concluir o cadastro preencha o perfil!", 1)
            }
            else if (result == "PP"){
                callback("Bem vindo/a!", 1)

            }
            else if( result == "PV"){
                callback("Preenchimento de todos os campos do perfil obrigratório", 2)

            }
            else{
                callback(result, 0)
            }


        }

    }

    fun getEmail(callback: (email: String) -> Unit){
        interactor.getEmail(callback)
    }

    fun consulta(callback: (perfil: Profile?) -> Unit){
        interactor.consulta(callback)
    }



    fun saveData(emailCampo: String, nomecompleto: String, telefone: String, username: String, callback: (result: String, id: Int) -> Unit){
            interactor.saveData(emailCampo, nomecompleto, telefone, username){ result ->
                if (result == "EMPTY DATA"){
                    callback("Todos os campos devem ser preenchidos!", 0)
                }
                else if(result == "SUCCESS"){
                    callback("Perfil salvo!", 1)

                }
                else if(result == "UID RECOVER FAIL"){
                    callback("Erro na recuperação da identificação do usuário", 0)
                }
                else{
                    //error
                    callback(result, 0)
                }
            }
    }


    fun deleteUser(callback: (result: String, id: Int) -> Unit){
        interactor.deleteUser{result ->
            if(result == "SUCCESS"){
                callback("Usuário excluido com sucesso", 1)
            }
            else{
                callback(result, 0)
            }

        }
    }

    fun changePassword(email: String, callback: (result: String, id: Int) -> Unit){
        interactor.changePassword(email){ result ->
            if(result == "EMPTY EMAIL"){
                callback("Digite o E-mail", 0)
            }
            else{
                callback("E-mail para recuperar senha enviado", 1)
            }
        }

    }

    fun showGuardians(callback: (guardians: Array<Guardian>?) -> Unit){
        interactor.showGuardians(callback)

    }

    fun registerGuardian(nome: String?, telefone: String?, email: String?, callback: (result: String) -> Unit){
        interactor.registerGuardian(nome, telefone, email){ result ->
            if(result == "SUCCESS"){
                callback("Guardião cadastrado!")

            }
            else if(result == "UID RECOVER FAIL"){
                callback("Erro na recuperação da identificação do usuário")
            }
            else{
                //error
                callback(result)
            }
        }

    }
}
