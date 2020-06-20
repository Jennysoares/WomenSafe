package br.com.victoriasantos.womensafe.interactor

import android.content.Context

import br.com.victoriasantos.womensafe.repository.DialogflowRepository

class DialogFlowInteractor(context: Context) {
    val repository = DialogflowRepository(context, "https://womensafe-dialogflow.herokuapp.com/" )

    fun sendTextMessage(text: String, sessionId: String, callback: (response: String?) -> Unit ){
        repository.sendTextMessage(text, sessionId, callback)
    }
}