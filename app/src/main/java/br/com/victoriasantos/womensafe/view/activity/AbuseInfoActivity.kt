package br.com.victoriasantos.womensafe.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.DialogFlowViewModel
import com.github.bassaer.chatmessageview.model.ChatUser
import com.github.bassaer.chatmessageview.model.Message
import kotlinx.android.synthetic.main.activity_abuse_info.*
import kotlinx.android.synthetic.main.item_guardians.*
import kotlin.random.Random

@Suppress("DEPRECATION")
class AbuseInfoActivity : AppCompatActivity() {
    val SessionId = (0..999999999999).random().toString()

    private val viewModel: DialogFlowViewModel by lazy {
        ViewModelProvider(this).get(DialogFlowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abuse_info)



        val human = ChatUser(
            1,
            "Você",
            BitmapFactory.decodeResource(
                resources,
                R.drawable.ic_account_circle
            )
        )

        val agent = ChatUser(
            2,
            "WomenSafe's Bot",
            BitmapFactory.decodeResource(
                resources,
                R.drawable.bot_profile
            )
        )

        my_chat_view.setInputTextHint("Digite 'Olá' para iniciar conversa.")
        my_chat_view.setRightBubbleColor(getColor(R.color.roxo))
        my_chat_view.setBackgroundDrawable(getDrawable(R.drawable.pagina_login))
        my_chat_view.setUsernameTextColor(Color.WHITE);
        my_chat_view.setSendTimeTextColor(Color.WHITE);
        my_chat_view.setDateSeparatorColor(Color.WHITE);

        my_chat_view.setOnClickSendButtonListener(
            View.OnClickListener { my_chat_view.send(Message.Builder().setUser(human).setText(my_chat_view.inputText).build())
                val text = my_chat_view.inputText
                my_chat_view.inputText =""
                my_chat_view.setInputTextHint("Mensagem")
                viewModel.sendTextMessage(text, SessionId) { response ->
                    if(response.isNullOrEmpty()){
                        Toast.makeText(this, applicationContext.getString(R.string.error), Toast.LENGTH_LONG).show()
                    }
                    else {
                        my_chat_view.send(
                            Message.Builder()
                                .setRight(true)
                                .setUser(agent)
                                .setText(response!!)
                                .build()
                        )
                    }
                }
            }
        )
    }
}


