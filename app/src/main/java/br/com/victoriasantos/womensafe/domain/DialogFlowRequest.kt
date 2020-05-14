package br.com.victoriasantos.womensafe.domain

data class DialogFlowRequest(
    val text: String,
     val email: String,
     val sessionId: String
)
