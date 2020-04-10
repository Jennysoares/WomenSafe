package br.com.victoriasantos.womensafe.interactor

import android.content.Context
import android.widget.Toast
import br.com.victoriasantos.womensafe.repository.FirebaseRepository

class FirebaseInterector(private val context: Context) {

     private val repository = FirebaseRepository(context)

    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit) {

        if (email == null) {
            callback("EN")
            return
        }
        if (email.isEmpty()) {
            callback("EV")
            return
        }
        if (senha == null) {
            callback("SN")
            return
        }
        if (senha.isEmpty()) {
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

    }
