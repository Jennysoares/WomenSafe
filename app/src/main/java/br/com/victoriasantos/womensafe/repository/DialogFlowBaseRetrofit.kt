package br.com.victoriasantos.womensafe.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class DialogFlowBaseRetrofit(context: Context, baseUrl: String){
        val retrofit: Retrofit
        val gson: Gson

        init {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build()

            gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()

            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun toJson(obj: Any): String = gson.toJson(obj)
        inline fun <reified T> fromJson(json: String) = gson.fromJson(json, T::class.java)
}
