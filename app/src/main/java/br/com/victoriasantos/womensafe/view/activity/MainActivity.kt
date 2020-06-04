package br.com.victoriasantos.womensafe.view.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest



class MainActivity : AppCompatActivity() {

    val REQUEST_PHONE_CALL = 1

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        primeira_opcao.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        segunda_opcao.setOnClickListener {
            startActivity(Intent(this, SafeRidesActivity::class.java))
        }

        terceira_opcao.setOnClickListener {
            startActivity(Intent(this, GuardiansActivity::class.java))
        }

        quarta_opcao.setOnClickListener {
            startActivity(Intent(this, ContributionsActivity::class.java))
        }

        quinta_opcao.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        sexta_opcao.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        botao_panico.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            }
            else{

                callPolice()
            }
        }

        logout.setOnClickListener {
            logout()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PHONE_CALL){
            callPolice()
        }
    }

    @SuppressLint("MissingPermission")
    fun callPolice(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar ligação")
        builder.setMessage("Você está prestes a ligar para polícia militar. Deseja continuar?")
        builder.apply {
            setPositiveButton("SIM", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:190")
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

    fun logout(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deseja realmente sair?")
        builder.apply {
            setPositiveButton("SIM", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    viewModel.logout{
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
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
