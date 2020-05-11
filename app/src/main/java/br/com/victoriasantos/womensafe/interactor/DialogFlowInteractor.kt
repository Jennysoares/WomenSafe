package br.com.victoriasantos.womensafe.interactor

import android.content.Context
import br.com.victoriasantos.womensafe.domain.DialogFlowResponse
import br.com.victoriasantos.womensafe.repository.DialogflowRepository

class DialogFlowInteractor(context: Context) {
    val repository = DialogflowRepository(context, "https://womensafe-dialogflow.herokuapp.com/" )

    fun sendTextMessage(text: String, email: String, sessionId: String, callback: (response: DialogFlowResponse) -> Unit ){
        repository.sendTextMessage(text, email, sessionId, callback)
        //TODO:
    }
}