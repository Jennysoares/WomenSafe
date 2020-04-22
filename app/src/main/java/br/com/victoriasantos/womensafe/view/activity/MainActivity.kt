package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.victoriasantos.womensafe.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quinta_opcao.setOnClickListener{
            val intentPerfil = Intent(this, ProfileActivity::class.java)
            startActivity(intentPerfil)
        }
    }
}
