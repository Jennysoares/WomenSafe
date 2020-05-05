package br.com.victoriasantos.womensafe.repository


import android.content.Context
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.domain.LocationData
import br.com.victoriasantos.womensafe.domain.Plate
import br.com.victoriasantos.womensafe.domain.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date


class FirebaseRepository(context: Context) {
    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private var profile: Profile? = null
    private var guardian: Guardian? = null


    fun cadastro(email: String, senha: String, callback: (result: String) -> Unit) {
        val operation = mAuth.createUserWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback("S")
            } else {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possível entrar no aplicativo no momento"
                callback(error)
            }
        }
    }


    fun consulta(callback: (snapshot: DataSnapshot?) -> Unit) {
        val email = mAuth.currentUser?.email
        val profiles = database.getReference("Users")
        val query = profiles.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }
        })
    }


    fun login(email: String, senha: String, callback: (result: String) -> Unit) {
        val operation = mAuth.signInWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback("S")
            } else {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possivel entrar o aplicativo"
                callback(error)
            }
        }
    }


    fun getEmail(callback: (email: String?) -> Unit) {
        val emailFinal = mAuth.currentUser?.email
        callback(emailFinal)
    }


    fun UpdateEmail(email: String, callback: (result: String) -> Unit) {

        val usuario = mAuth.currentUser

        usuario?.updateEmail(email)?.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                val error = task.exception?.localizedMessage
                    ?: "Houve um erro ao atualizar o e-mail!"
                callback(error)
            } else {
                callback("S")
            }
        }
    }


    fun saveData(
        email: String,
        nomecompleto: String,
        telefone: String,
        username: String,
        callback: (result: String) -> Unit
    ) {
        profile = Profile(
            email = email,
            nomecompleto = nomecompleto,
            telefone = telefone,
            username = username
        )

        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            // Variável que define qual nó será atualizado, nesse caso será o nó "Usuários"
            val userprofile =
                database.getReference("Users/$uid") // $uid é o onde será substituido pelo id do usuário logado
            userprofile.setValue(profile) //Atualiza/cria os dados
            callback("SUCCESS")

        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun deleteUser(callback: (result: String) -> Unit) {
        //exclui a conta na autenticação
        val usuario = mAuth.currentUser
        var erro = 0;
        var message = ""

        usuario?.delete()?.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possível excluir este usuário!"
                erro = 1
                message = error
            }
        }
        // exclui no banco de dados
        val uid = usuario?.uid
        val dados = database.getReference("Users/$uid")

        dados.removeValue().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                val error = task.exception?.localizedMessage
                    ?: "Não foi possível excluir os dados!"
                erro = 1
                message = error
            }
        }

        if (erro == 0) {
            callback("SUCCESS")
        } else {
            callback(message)
        }
    }


    fun changePassword(email: String) {
        mAuth.sendPasswordResetEmail(email)
    }


    fun showGuardians(callback: (snapshot: DataSnapshot?) -> Unit) {

        val ref = database.getReference("Users/${mAuth.currentUser?.uid}/guardians")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }
        })
    }


    fun registerGuardian( nome: String?, telefone: String?, email: String?, callback: (result: String) -> Unit) {
        guardian = Guardian(
            nome = nome,
            telefone = telefone,
            email = email
        )

        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            val userguardian = database.getReference("Users/$uid/guardians")
            userguardian.push().setValue(guardian)
            callback("SUCCESS")
        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun getGuardiansCount(callback: (qtd: Long) -> Unit) {
        val ref = database.getReference("Users/${mAuth.currentUser?.uid}/guardians")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(0)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.childrenCount)
            }
        })
    }

    fun deleteGuardian(email: String?, callback: (result: String) -> Unit) {
        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            val userguardian = database.getReference("Users/$uid/guardians/")
            userguardian.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { guardian ->
                        if (guardian.child("email").value?.equals(email)!!) {
                            guardian.ref.removeValue()
                            callback("SUCCESS")
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    callback("ERROR")
                }
            })
        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun registerPlate(placa: String, comentario: String, callback: (result: String) -> Unit) {
        val uid = mAuth.currentUser?.uid
        if (!uid.isNullOrBlank()) {
            val Plate = Plate(
                placa = placa,
                comentario = comentario
            )

            val platePath = database.getReference("Plate/$uid")
            platePath.push().setValue(Plate)
            callback("SUCCESS")
        } else {
            callback("ERROR")
        }
    }

    fun showPlate(child: Int, placa: String?, callback: (snapshot: DataSnapshot?) -> Unit) {
        val uid = mAuth.currentUser?.uid
       // var ref: Query? = null

        //if (child == 1) { // busca pela placa
            // ref = database.getReference("Plate").orderByChild("placa").equalTo(placa)
        //} else if (child == 2) { // busca pelo uid
          val ref = database.getReference("Plate/$uid").orderByKey()
        //}

        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }
        })
    }

    fun deletePlate(placa: String?, comentario: String?, callback: (result: String) -> Unit) {
        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            val plates = database.getReference("Plate/$uid/")
            plates.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { plate ->
                        if (plate.child("placa").value?.equals(placa)!! && plate.child("comentario").value?.equals(
                                comentario
                            )!!
                        ) {
                            plate.ref.removeValue()
                            callback("SUCCESS")
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    callback("ERROR")
                }
            })
        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun spotRegister(latitude: String, longitude: String, comentario: String?, callback: (result: String) -> Unit) {
        val uid = mAuth.currentUser?.uid
        val location = LocationData(
            evaluation = comentario,
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble(),
            uid = uid
        )
        if(uid != null) {
            database.getReference("Location").child(Date().time.toString()).setValue(location)
            callback("SUCCESS")
        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun showSpotEvaluation(child: Int, cep: String?, callback: (snapshot: DataSnapshot?) -> Unit) {
        val uid = mAuth.currentUser?.uid
       // var ref: Query? = null

       // if (child == 1) { // sem busca com base em filhos
           // ref = database.getReference("Location").orderByKey()
        //} else if (child == 2) { // busca pelo uid
           val ref = database.getReference("Location").orderByChild("uid").equalTo(uid)
        //}


        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }
        })
    }

    fun deleteSpotEvaluation(
        latitude: Double?,
        longitude: Double?,
        evaluation: String?,
        callback: (result: String) -> Unit
    ) {
        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            val spotEvaluation = database.getReference("Location").orderByChild("uid").equalTo(uid)
            spotEvaluation.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { spot ->
                        if (spot.child("latitude").value?.equals(latitude)!! && spot.child("longitude").value?.equals(longitude)!!) {
                            if (spot.child("evaluation").value?.equals(evaluation)!!) {
                                spot.ref.removeValue()
                                callback("SUCCESS")
                            }
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    callback("ERROR")
                }
            })
        } else {
            callback("UID RECOVER FAIL")
        }
    }

    fun getMarkers(callback: (snapshot: DataSnapshot?) -> Unit) {

        val ref = database.getReference("Location")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }
        })
    }

    fun showEvaluations(callback: (locations: Array<LocationData>?) -> Unit){

        val ref = database.getReference("Location")
        val locations = mutableListOf<LocationData>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                    callback(null)
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot != null && snapshot.hasChildren() == true) {
                    snapshot.children.forEach{ l ->
                        val location = l.getValue(LocationData::class.java)
                        locations.add(location!!)
                    }
                    callback(locations.toTypedArray())
                }
                callback(null)
            }
        })
    }

}
