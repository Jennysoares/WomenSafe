package br.com.victoriasantos.womensafe.view.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_send_location.*

class SendLocationActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_location)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        getGuardiansNames(latitude, longitude)
    }

    fun getGuardiansNames(latitude: Double, longitude: Double) {
        viewModel.showGuardians { guardians ->
            if (guardians != null) {
                nome1.text = guardians.component1().nome
                if (guardians.size >= 2) {
                    nome2.text = guardians.component2().nome
                    nome2.setOnClickListener {
                        sendLocation(latitude, longitude, guardians.component2())
                    }

                    if (guardians.size >= 3) {
                        nome3.text = guardians.component3().nome
                        nome3.setOnClickListener {
                            sendLocation(latitude, longitude, guardians.component3())
                        }
                    }
                }

                nome1.setOnClickListener {
                    sendLocation(latitude, longitude, guardians.component1())
                }

            } else {
                Toast.makeText(this, getString(R.string.empty_guardians), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sendLocation(latitude: Double, longitude: Double, guardian: Guardian) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.share_loc))
        builder.setMessage(getString(R.string.alert_shareloc))

        builder.apply {
            setPositiveButton(getString(R.string.yes), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    viewModel.getGuardianNumber(guardian) { smsNumber ->
                        try {
                            val latitude = latitude.toString()
                            val longitude = longitude.toString()
                            val waIntent = Intent(Intent.ACTION_VIEW)
                            val whatsAppMessage = "https://maps.google.com/?q=$latitude,$longitude"
                            waIntent.data =
                                Uri.parse("http://api.whatsapp.com/send?phone=$smsNumber&text=Minha localiza????o: $whatsAppMessage")
                            startActivity(waIntent)
                            sendToast1()
                        } catch (e: PackageManager.NameNotFoundException) {
                            sendToast2()
                        }
                    }

                }
            })
            setNegativeButton(getString(R.string.no), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {

                }
            })
        }
        builder.show()

    }

    fun sendToast1(){
        Toast.makeText(this, getString(R.string.loc_sent), Toast.LENGTH_LONG).show()
    }

    fun sendToast2(){
        Toast.makeText(this, getString(R.string.wpp_notinsta), Toast.LENGTH_SHORT).show()
    }
}
