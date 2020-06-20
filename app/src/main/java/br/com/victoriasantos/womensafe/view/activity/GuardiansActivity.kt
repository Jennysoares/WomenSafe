package br.com.victoriasantos.womensafe.view.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.view.adapter.GuardianAdapter
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_guardians.*

class GuardiansActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    val REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardians)
        pBar.visibility = GONE

        bt_CadastroGuardiao.setOnClickListener {
            startActivity(Intent(this, NewGuardianActivity::class.java))
        }
        configureRecyclerView()
        pBar.visibility = VISIBLE
        showGuardians()

    }

    private fun configureRecyclerView(){
        recycleView_guardian.layoutManager = LinearLayoutManager(this)

    }

     fun showGuardians(){

        viewModel.showGuardians{ guardians ->
            val adapter = GuardianAdapter(this,this, guardians)
            recycleView_guardian.adapter = adapter
            if(guardians.isNullOrEmpty()){
                Toast.makeText(this, "Você não possui guardiões!",Toast.LENGTH_LONG).show()
                pBar.visibility = GONE
            }
            pBar.visibility = GONE
        }
    }

    fun deleteGuardian(email : String?){
        pBar.visibility = VISIBLE
        viewModel.deleteGuardian(email){result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
            pBar.visibility = GONE
            showGuardians()
        }
    }

    @SuppressLint("MissingPermission")
    fun callGuardian(numero : String?){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar ligação")
        builder.setMessage("Você está prestes a ligar para seu guardião. Deseja continuar?")
        builder.apply {
            setPositiveButton("SIM", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$numero")
                    startActivity(intent)
                }
            })
            setNegativeButton("NÃO", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
        }
        builder.show()
    }

}
