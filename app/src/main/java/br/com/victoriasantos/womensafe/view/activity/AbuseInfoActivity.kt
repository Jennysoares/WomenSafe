package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.DialogFlowViewModel
import kotlinx.android.synthetic.main.activity_abuse_info.*

class AbuseInfoActivity : AppCompatActivity() {

    private val viewModel: DialogFlowViewModel by lazy {
        ViewModelProvider(this). get(DialogFlowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abuse_info)

        link1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://catracalivre.com.br/carnaval-sem-assedio/veja-como-agir-caso-voce-seja-vitima-de-assedio-ou-estupro/")
            startActivity(intent) }

        link2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.mulher.df.gov.br/centro-especializado-de-atendimento-a-mulher-ceam/")
            startActivity(intent) }

        link3.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://delegaciaeletronica.pcdf.df.gov.br/")
            startActivity(intent) }
    }
}


