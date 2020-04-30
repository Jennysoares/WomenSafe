package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.domain.Guardian
import br.com.victoriasantos.womensafe.view.adapter.GuardianAdapter
import br.com.victoriasantos.womensafe.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_guardians.*
import kotlinx.android.synthetic.main.item_guardians.*

class GuardiansActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardians)

        bt_CadastroGuardiao.setOnClickListener {
            startActivity(Intent(this, NewGuardianActivity::class.java))
        }
        configureRecyclerView()
        showGuardians()

    }

    private fun configureRecyclerView(){
        recycleView_guardian.layoutManager = LinearLayoutManager(this)

    }


    fun showGuardians(){

        viewModel.showGuardians{ guardians ->
            if(guardians.isNullOrEmpty()){
                Toast.makeText(this, "Você não possui guardiões ainda",Toast.LENGTH_LONG).show()
            }else{
                val adapter = GuardianAdapter(guardians)
                recycleView_guardian.adapter = adapter
            }
        }
    }
}
