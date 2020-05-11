package br.com.victoriasantos.womensafe.repository

import android.content.Context
import br.com.victoriasantos.womensafe.domain.DialogFlowRequest
import br.com.victoriasantos.womensafe.domain.DialogFlowResponse
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
    ): Call<DialogFlowResponse>
}

class DialogflowRepository(context: Context, baseUrl: String) : DialogFlowBaseRetrofit(context, baseUrl) {
    private val service = retrofit.create(DialogflowService::class.java)

    fun sendTextMessage(text: String, email: String, sessionId: String, callback: (response: DialogFlowResponse)-> Unit) {
        val request = DialogFlowRequest(text, email, sessionId)
        service.sendTextMessage(request).enqueue(object : Callback<DialogFlowResponse> {

            override fun onResponse(call: Call<DialogFlowResponse>, response: Response<DialogFlowResponse>) {
                val result = response.body()

                //callback()
            }

            override fun onFailure(call: Call<DialogFlowResponse>, t: Throwable) {
                //callback()
            }
        })
    }
}
