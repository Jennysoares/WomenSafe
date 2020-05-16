package br.com.victoriasantos.womensafe.interactor

import android.content.Context
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.domain.LocationData
import br.com.victoriasantos.womensafe.domain.Plate
import br.com.victoriasantos.womensafe.domain.Profile
import br.com.victoriasantos.womensafe.repository.FirebaseRepository
import java.text.SimpleDateFormat
import java.util.*


class FirebaseInterector(private val context: Context) {

    private val repository = FirebaseRepository(context)
    private var profile: Profile? = null

    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit) {

        if (email.isNullOrBlank()) {
            callback(context.getString(R.string.email_vazio))
            return
        }
        if (senha.isNullOrBlank()) {
            callback(context.getString(R.string.senha_vazia))
            return
        } else {
            if (senha.length < 6) {
                callback(context.getString(R.string.senha_tamanho_invalido))
                return
            }
        }
        repository.cadastro(email, senha, callback)
    }

    fun login(email: String, senha: String, callback: (result: String) -> Unit) {

        if (email.isNullOrBlank()) {
            callback(context.getString(R.string.email_vazio))
            return
        }
        if (senha.isNullOrBlank()) {
            callback(context.getString(R.string.senha_vazia))
            return
        } else {
            if (senha.length < 6) {
                callback(context.getString(R.string.senha_tamanho_invalido))
                return
            }
        }
        repository.login(email, senha) { result ->
            if (result == "S") {
                repository.consulta { snapshot ->
                    if (snapshot != null && snapshot.hasChildren() == true) {// Verifica se possui dados{
                        profile = snapshot.children.first().getValue(Profile::class.java)
                        if (profile?.nomecompleto.toString()
                                .isNotEmpty() && profile?.telefone.toString()
                                .isNotEmpty() && profile?.username.toString().isNotEmpty()
                        ) {
                            callback("PP")
                        } else {
                            callback("PV")
                        }
                    } else {
                        callback("PV")
                    }
                }
            } else {
                callback(result)
            }
        }
    }

    fun getEmail(callback: (email: String) -> Unit) {
        repository.getEmail { email ->
            if (email != null) {
                callback(email)
            }
        }
    }

    fun consulta(callback: (perfil: Profile?) -> Unit) {
        repository.consulta { snapshot ->
            if (snapshot != null && snapshot.hasChildren() == true) {
                profile = snapshot.children.first().getValue(Profile::class.java)
                if (profile != null) {
                    callback(profile)
                } else {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }

    fun saveData(
        emailCampo: String,
        nomecompleto: String,
        telefone: String,
        username: String,
        callback: (result: String) -> Unit
    ) {
        // POSSO ALTERAR PRA EMAIL VAZIO -> CONCERTAR
        repository.getEmail { emailFinal ->
            if (!emailFinal.equals(emailCampo)) { // Pergunta se o email do usuario antigo(emailFinal) é diferente(por ter uma ! no início) ao email que está no campo da página(emailCampo)
                val email = emailCampo
                repository.UpdateEmail(email) { result ->
                    if (result == "S") {
                        if (nomecompleto.isNotEmpty() && telefone.isNotEmpty() && username.isNotEmpty()) {
                            repository.saveData(email, nomecompleto, telefone, username, callback)
                        } else {
                            callback("EMPTY DATA")
                            // Todos os campos devem ser preenchidos!"
                        }

                    } else {
                        callback(result)
                    }

                }
            } else {
                val email = emailCampo
                if (nomecompleto.isNotEmpty() && telefone.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()) {
                    repository.saveData(email, nomecompleto, telefone, username, callback)
                } else {
                    callback("EMPTY DATA")
                    // Todos os campos devem ser preenchidos!"
                }

            }
        }
    }

    fun deleteUser(callback: (result: String) -> Unit) {
        repository.deleteUser(callback)
    }

    fun changePassword(email: String, callback: (result: String) -> Unit) {
        if (email != "") {
            repository.changePassword(email)
            callback("EMAIL SENT")
        } else {
            callback("EMPTY EMAIL")
        }
    }

    fun showGuardians(callback: (guardians: Array<Guardian>?) -> Unit) {
        repository.showGuardians { snapshot ->
            val guardians = mutableListOf<Guardian>()
            if (snapshot != null && snapshot.hasChildren() == true) {
                snapshot.children.forEach { g ->
                    val guardian = g.getValue(Guardian::class.java)
                    guardians.add(guardian!!)
                }
                callback(guardians.toTypedArray())
            } else {
                callback(null)
            }
        }
    }

    fun registerGuardian(
        nome: String?,
        telefone: String?,
        email: String?,
        callback: (result: String) -> Unit
    ) {
        if (email.isNullOrBlank()) {
            callback("EV")
        } else if (telefone.isNullOrBlank()) {
            callback("TF")
        } else if (telefone.length < 15) {
            callback("TI")
        } else if (nome.isNullOrBlank()) {
            callback("NV")
        } else {
            repository.getGuardiansCount { qtd ->
                if (qtd < 3) {
                    repository.registerGuardian(nome, telefone, email) { result ->
                        if (result.equals("SUCCESS")) {
                            callback("S")
                        } else {
                            callback("NP")
                        }
                    }
                } else {
                    callback("LR")
                }
            }
        }
    }

    fun deleteGuardian(email: String?, callback: (result: String) -> Unit) {
        repository.deleteGuardian(email) { result ->
            if (result.equals("SUCCESS")) {
                callback("S")
            } else {
                callback("Error")
            }
        }
    }

    fun registerPlate(
        placa: String?,
        placaUpdate: String?,
        comentario: String?,
        comentarioUpdate: String?,
        child: Int,
        callback: (result: String) -> Unit
    ) {
        if (placaUpdate.isNullOrBlank()) {
            callback("BLANK PLATE")
            return
        } else if (placaUpdate.length != 8) {
            callback("INVALID LENGTH PLATE")
            return
        } else if (comentarioUpdate.isNullOrBlank()) {
            callback("BLANK COMMENT")
            return
        } else if (comentarioUpdate.length < 100) {
            callback("INVALID LENGTH COMMENT")
            return
        } else {
            repository.registerPlate(placa, placaUpdate, comentario, comentarioUpdate, child) { result ->
                if (result.equals("SUCCESS")) {
                    callback(result)
                } else {
                    callback("ERROR")
                }
            }
        }
    }

    fun showPlate(child: Int, placa: String?, callback: (plates: Array<Plate>?) -> Unit) {
        repository.showPlate(child, placa) { snapshot ->
            val plates = mutableListOf<Plate>()
            if (snapshot != null && snapshot.hasChildren() == true) {
                snapshot.children.forEach { g ->
                    val plate = g.getValue(Plate::class.java)
                    plates.add(plate!!)
                }

                val ordered =  QuickSort(context).quicksort(plates)
                callback(ordered.toTypedArray())
            } else {
                callback(null)
            }
        }
    }

    fun deletePlate(placa: String?, comentario: String?, callback: (result: String) -> Unit) {
        repository.deletePlate(placa, comentario) { result ->
            if (result.equals("SUCCESS")) {
                callback("S")
            } else {
                callback("Error")
            }
        }
    }

    fun spotRegister(latitude: String?, longitude: String?, comentario: String?, comentarioUpdate: String?, data: String?, child: Int, callback: (result: String) -> Unit) {
        if (comentarioUpdate.isNullOrBlank()) {
            callback("EVALUATION EMPTY")
        } else if (comentarioUpdate.length < 100) {
            callback("EVALUATION SHORT")
        } else {
            repository.spotRegister(latitude, longitude, comentario, comentarioUpdate, data, child, callback)
        }
    }

    fun showSpotEvaluation(callback: (spots: Array<LocationData>?) -> Unit) {
        repository.showSpotEvaluation { snapshot ->
            val spots = mutableListOf<LocationData>()
            if (snapshot != null && snapshot.hasChildren() == true) {
                snapshot.children.forEach { g ->
                    val spot = g.getValue(LocationData::class.java)
                    spots.add(spot!!)
                }
                callback(spots.toTypedArray())
            } else {
                callback(null)
            }
        }
    }

    fun deleteSpotEvaluation(
        latitude: Double?,
        longitude: Double?,
        evaluation: String?,
        callback: (result: String) -> Unit
    ) {
        repository.deleteSpotEvaluation(latitude, longitude, evaluation) { result ->
            if (result.equals("SUCCESS")) {
                callback("S")
            } else {
                callback("Error")
            }
        }
    }

    fun getMarkers(callback: (locations: Array<LocationData>?) -> Unit) {
        repository.getMarkers { snapshot ->
            val locations = mutableListOf<LocationData>()
            if (snapshot != null && snapshot.hasChildren() == true) {
                snapshot.children.forEach { l ->
                    val location = l.getValue(LocationData::class.java)
                    if (location != null) {
                        locations.add(location)
                    }
                }
                callback(locations.toTypedArray())
            } else {
                callback(null)
            }
        }
    }

    fun showEvaluations(latitude: Double, longitude: Double, callback: (evaluations: Array<LocationData>?) -> Unit) {
        repository.getMarkers { snapshot ->
            val locations = mutableListOf<LocationData>()
            if (snapshot != null && snapshot.hasChildren() == true) {
                snapshot.children.forEach { l ->
                    val location = l.getValue(LocationData::class.java)
                    if (location != null) {
                        locations.add(location)
                    }
                }
            } else {
                callback(null)
            }
            val evaluations = mutableListOf<LocationData>()
            locations?.forEach { c ->
                if (c.latitude == latitude && c.longitude == longitude) {
                    evaluations.add(c!!)
                }
            }

            callback(evaluations.reversed().toTypedArray())

        }
    }

}

