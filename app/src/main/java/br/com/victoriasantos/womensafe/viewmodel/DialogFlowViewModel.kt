package br.com.victoriasantos.womensafe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.victoriasantos.womensafe.domain.DialogFlowResponse
import br.com.victoriasantos.womensafe.interactor.DialogFlowInteractor

class DialogFlowViewModel(val app: Application) : AndroidViewModel(app){
    val interactor = DialogFlowInteractor(app.applicationContext)
    fun sendTextMessage(text: String, email: String, sessionId: String, callback: (response: DialogFlowResponse) -> Unit ){
        interactor.sendTextMessage(text, email, sessionId){ response ->

        }

    }

}