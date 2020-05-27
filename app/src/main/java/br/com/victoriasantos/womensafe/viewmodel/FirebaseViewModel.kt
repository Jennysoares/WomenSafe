package br.com.victoriasantos.womensafe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.domain.LocationData
import br.com.victoriasantos.womensafe.domain.Plate
import br.com.victoriasantos.womensafe.domain.Profile
import br.com.victoriasantos.womensafe.interactor.FirebaseInterector
import com.google.android.gms.maps.model.LatLng

class FirebaseViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = FirebaseInterector(app.applicationContext)

    fun cadastro(email: String, senha: String, confimacao: String, callback: (result: String, id: Int) -> Unit) {
        interactor.cadastro(email, senha, confimacao) { result ->

            if (result == "EV") {
                callback(app.applicationContext.getString(R.string.email_required), 0)
            } else if (result == "SV") {
                callback(app.applicationContext.getString(R.string.password_required), 0)
            } else if (result == "SC") {
                callback(app.applicationContext.getString(R.string.short_password), 0)
            } else if (result == "S") {
                callback(app.applicationContext.getString(R.string.auth_email), 1)
            } else if (result == "SD"){
                callback(app.applicationContext.getString(R.string.diferent_passwords), 0)
            }
            else {
                callback(result, 0)
            }
        }
    }

    fun login(email: String, senha: String, callback: (result: String, id: Int) -> Unit) {

        interactor.login(email, senha) { result ->

            if (result == "EV") {
                callback(app.applicationContext.getString(R.string.email_required), 0)
            } else if (result == "SV") {
                callback(app.applicationContext.getString(R.string.password_required), 0)
            } else if ("password is invalid" in result) {
                callback(app.applicationContext.getString(R.string.invalid_password), 0)
            } else if (result == "SC") {
                callback(app.applicationContext.getString(R.string.short_password), 0)
            } else if (result == "S") {
                callback(app.applicationContext.getString(R.string.concluir_cadastro), 1)
            } else if (result == "PP") {
                callback(app.applicationContext.getString(R.string.welcome), 1)
            } else if (result == "PV") {
                callback(app.applicationContext.getString(R.string.required_fiel_profile), 2)
            } else {
                callback(result, 0)
            }
        }
    }

    fun getEmail(callback: (email: String) -> Unit) {
        interactor.getEmail(callback)
    }

    fun consulta(callback: (perfil: Profile?) -> Unit) {
        interactor.consulta(callback)
    }

    fun saveData(
        emailCampo: String,
        nomecompleto: String,
        telefone: String,
        username: String,
        callback: (result: String, id: Int) -> Unit
    ) {
        interactor.saveData(emailCampo, nomecompleto, telefone, username) { result ->
            if (result == "EMPTY DATA") {
                callback(app.applicationContext.getString(R.string.field_required), 0)
            } else if (result == "SUCCESS") {
                callback(app.applicationContext.getString(R.string.save_profile), 1)

            } else if (result == "UID RECOVER FAIL") {
                callback(app.applicationContext.getString(R.string.error_id_user), 0)
            } else {
                //error
                callback(result, 0)
            }
        }
    }

    fun deleteUser(callback: (result: String, id: Int) -> Unit) {
        interactor.deleteUser { result ->
            if (result == "SUCCESS") {
                callback(app.applicationContext.getString(R.string.user_delete_sucess), 1)
            } else {
                callback(result, 0)
            }

        }
    }

    fun changePassword(email: String, callback: (result: String, id: Int) -> Unit) {
        interactor.changePassword(email) { result ->
            if (result == "EMPTY EMAIL") {
                callback(app.applicationContext.getString(R.string.write_email), 0)
            } else {
                callback(app.applicationContext.getString(R.string.recover_password_success), 1)
            }
        }
    }

    fun showGuardians(callback: (guardians: Array<Guardian>?) -> Unit) {
        interactor.showGuardians(callback)

    }

    fun registerGuardian(
        nome: String?,
        telefone: String?,
        email: String?,
        callback: (result: String) -> Unit
    ) {
        interactor.registerGuardian(nome, telefone, email) { result ->
            if (result.equals("S")) {
                callback(app.applicationContext.getString(R.string.guardian_registered))

            } else if (result.equals("NP")) {
                callback(app.applicationContext.getString(R.string.error))
            } else if (result.equals("EV")) {
                callback(app.applicationContext.getString(R.string.email_required))
            } else if (result.equals("TF")) {
                callback(app.applicationContext.getString(R.string.phone_required))
            } else if (result.equals("TI")) {
                callback(app.applicationContext.getString(R.string.phone_invalid))
            } else if (result.equals("NV")) {
                callback(app.applicationContext.getString(R.string.name_required))
            } else {
                callback(app.applicationContext.getString(R.string.limit_guardians))
            }
        }
    }

    fun deleteGuardian(email: String?, callback: (result: String) -> Unit) {
        interactor.deleteGuardian(email) { result ->
            if (result.equals("S")) {
                callback(app.applicationContext.getString(R.string.guardian_remove_success))
            } else {
                callback(app.applicationContext.getString(R.string.error_remove_guardian))
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
        interactor.registerPlate(placa, placaUpdate, comentario, comentarioUpdate, child) { result ->
            if (result.equals("BLANK PLATE")) {
                callback(app.applicationContext.getString(R.string.request_plate))
            } else if (result.equals("INVALID LENGTH PLATE")) {
                callback(app.applicationContext.getString(R.string.invalid_plate))
            } else if (result.equals("BLANK COMMENT")) {
                callback(app.applicationContext.getString(R.string.plate_comment_required))
            } else if (result.equals("INVALID LENGTH COMMENT")) {
                callback(app.applicationContext.getString(R.string.length_comment_plate_required))
            } else if (result.equals("SUCCESS")) {
                if (child == 1) {
                    callback(app.applicationContext.getString(R.string.plate_registered_success))
                } else if (child == 2) {
                    callback(app.applicationContext.getString(R.string.plate_updated_ok))
                }
            } else if (result.equals("ERROR")) {
                callback(app.applicationContext.getString(R.string.error_plate_register))
            }

        }
    }

    fun showPlate(child: Int, placa: String?, callback: (plates: Array<Plate>?) -> Unit) {
        interactor.showPlate(child, placa, callback)
    }

    fun deletePlate(placa: String?, comentario: String?, callback: (result: String) -> Unit) {
        interactor.deletePlate(placa, comentario) { result ->
            if (result.equals("S")) {
                callback(app.applicationContext.getString(R.string.plate_successfully_removed))
            } else {
                callback(app.applicationContext.getString(R.string.error_delete_plate))
            }
        }
    }

    fun spotRegister(
        latitude: String?,
        longitude: String?,
        comentario: String?,
        comentarioUpdate: String?,
        data : String?,
        child: Int,
        callback: (result: String, id: Int) -> Unit
    ) {
        interactor.spotRegister(latitude, longitude, comentario, comentarioUpdate, data, child) { result ->
            if (result == "UID RECOVER FAIL") {
                callback(app.applicationContext.getString(R.string.error_id_user), 0)
            } else if (result == "EVALUATION EMPTY") {
                callback(app.applicationContext.getString(R.string.required_SpotEvaluation), 0)
            } else if (result == "EVALUATION SHORT") {
                callback(app.applicationContext.getString(R.string.SpotEvaluation_too_short), 0)
            } else {
                if(child == 1){
                    callback(app.applicationContext.getString(R.string.SpotEvaluation_register_ok), 1)
                } else if(child == 2){
                    callback(app.applicationContext.getString(R.string.spot_update_ok),1)
                }
            }
        }
    }

    fun showSpotEvaluation(callback: (spots: Array<LocationData>?) -> Unit) {
        interactor.showSpotEvaluation(callback)
    }

    fun deleteSpotEvaluation(
        latitude: Double?,
        longitude: Double?,
        evaluation: String?,
        callback: (result: String) -> Unit
    ) {
        interactor.deleteSpotEvaluation(latitude, longitude, evaluation) { result ->
            if (result.equals("S")) {
                callback(app.applicationContext.getString(R.string.SpotEvaluation_successfully_delete))
            } else {
                callback(app.applicationContext.getString(R.string.error_SpotEvaluation_delete))
            }
        }
    }

    fun getMarkers(callback: (markers: Array<LatLng>?) -> Unit) {
        interactor.getMarkers { l ->
            val markers = mutableListOf<LatLng>()
            l?.forEach { c ->
                val marker = LatLng(c.latitude, c.longitude)
                markers.add(marker)
            }
            callback(markers.toTypedArray())
        }
    }

    fun showEvaluations(latitude: Double, longitude: Double, callback: (evaluations: Array<LocationData>?) -> Unit) {
        interactor.showEvaluations(latitude, longitude, callback)
    }

    fun getGuardianNumber(guardian: Guardian, callback: (numero: String) -> Unit) {
        var smsNumber = "55" + guardian.telefone.toString()
        smsNumber = smsNumber.replace("(", "")
        smsNumber = smsNumber.replace(")", "")
        smsNumber = smsNumber.replace(" ", "")
        smsNumber = smsNumber.replace("-", "")
        callback(smsNumber)
    }

}
