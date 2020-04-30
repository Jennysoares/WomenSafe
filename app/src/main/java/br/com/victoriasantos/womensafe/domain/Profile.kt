package br.com.victoriasantos.womensafe.domain

data class Profile (
    var email: String? = null,
    var username: String? = null,
    var nomecompleto: String? = null,
    var telefone: String? = null,
    var guardians: Guardian? = null
)