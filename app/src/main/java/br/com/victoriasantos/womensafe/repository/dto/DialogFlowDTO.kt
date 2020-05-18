package br.com.victoriasantos.womensafe.repository.dto

data class DialogFlowResult(
    val queryResult: Message? = null
)

data class Message(
    val fulfillmentMessages: Text? = null
)
data class Text(
    val text: String? = null
)
