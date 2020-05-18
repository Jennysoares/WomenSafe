package br.com.victoriasantos.womensafe.repository.dto

data class DialogFlowResult(
    val queryResult: Message? = null
)

data class Message(
    val fulfillmentText: String? = null
)

