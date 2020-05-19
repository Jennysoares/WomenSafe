package br.com.victoriasantos.womensafe.repository

import android.content.Context
import android.util.Log
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
    ): Call<List<DialogFlowResult>>
}

class DialogflowRepository(context: Context, baseUrl: String) : DialogFlowBaseRetrofit(context, baseUrl) {
    private val service = retrofit.create(DialogflowService::class.java)
    private val mAuth = FirebaseAuth.getInstance()


    fun sendTextMessage(text: String, sessionId: String, callback: (response: String?)-> Unit) {

        val email = mAuth.currentUser?.email!!
        val request = DialogFlowRequest(text, email, sessionId)
        service.sendTextMessage(request).enqueue(object : Callback<List<DialogFlowResult>> {

            override fun onResponse(call: Call<List<DialogFlowResult>>, response: Response<List<DialogFlowResult>>) {
                val result = response.body()
                var message:String? = null
                result?.forEach { m ->
                    if(m != null){
                        if(m.queryResult?.fulfillmentText != null){
                            message = m.queryResult?.fulfillmentText.toString()
                        }
                    }
                }
                callback(message)
            }
            override fun onFailure(call: Call<List<DialogFlowResult>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
