package br.com.victoriasantos.womensafe.view.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_main.*




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

        getUsername()

    }

    override fun onResume(){
        super.onResume()
        getUsername()
    }

    private fun getUsername(){
        viewModel.consulta { perfil ->
            username.text = perfil?.username
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if( requestCode == REQUEST_PHONE_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callPolice()
        }
    }

    @SuppressLint("MissingPermission")
    fun callPolice(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.confirmar_ligacao))
        builder.setMessage(getString(R.string.ligacao_continuar))
        builder.apply {
            setPositiveButton("SIM", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:190")
                    startActivity(intent)
                }
            })
            setNegativeButton("N??O", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
        }
        builder.show()
    }

    fun logout(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.realmente_sair))
        builder.apply {
            setPositiveButton("SIM", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    viewModel.logout{
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            })
            setNegativeButton("N??O", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
        }
        builder.show()
    }
}
