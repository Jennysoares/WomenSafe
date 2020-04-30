package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val adapter = GuardianAdapter(this,this, guardians)
            recycleView_guardian.adapter = adapter
            if(guardians.isNullOrEmpty()){
                Toast.makeText(this, "Você não possui guardiões!",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun deleteGuardian(email : String?){
        viewModel.deleteGuardian(email){result ->
            Toast.makeText(this,result, Toast.LENGTH_LONG).show()
            showGuardians()
        }
    }


}
