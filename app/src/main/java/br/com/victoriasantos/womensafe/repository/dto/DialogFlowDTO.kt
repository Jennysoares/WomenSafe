package br.com.victoriasantos.womensafe.repository.dto


data class DialogFlowResult(
    val queryResult: QueryResult? = null
)
data class QueryResult(
    val fulfillmentText: String? = null
)

