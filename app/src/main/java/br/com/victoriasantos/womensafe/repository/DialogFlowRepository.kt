package br.com.victoriasantos.womensafe.repository

import android.content.Context
import br.com.victoriasantos.womensafe.domain.DialogFlowRequest
import br.com.victoriasantos.womensafe.repository.dto.DialogFlowResult
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DialogflowService {

    @POST("api/message/text/send")
    @Headers("Content-Type: application/json")
    fun sendTextMessage(
        @Body request: DialogFlowRequest
    ): Call<DialogFlowResult>
}

class DialogflowRepository(context: Context, baseUrl: String) : DialogFlowBaseRetrofit(context, baseUrl) {
    private val service = retrofit.create(DialogflowService::class.java)
    private val mAuth = FirebaseAuth.getInstance()


    fun sendTextMessage(text: String, sessionId: String, callback: (response: String?)-> Unit) {

        val email = mAuth.currentUser?.email!!
        val request = DialogFlowRequest(text, email, sessionId)
        service.sendTextMessage(request).enqueue(object : Callback<DialogFlowResult> {

            override fun onResponse(call: Call<DialogFlowResult>, response: Response<DialogFlowResult>) {
                val result = response.body()?.queryResult?.fulfillmentMessages
                val message =  result?.text
                callback(message)
            }

            override fun onFailure(call: Call<DialogFlowResult>, t: Throwable) {
                callback(null)
            }
        })
    }
}
