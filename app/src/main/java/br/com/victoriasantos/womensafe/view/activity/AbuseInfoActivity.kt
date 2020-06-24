package br.com.victoriasantos.womensafe.view.activity


import android.graphics.BitmapFactory
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import br.com.victoriasantos.womensafe.R
import br.com.victoriasantos.womensafe.viewmodel.DialogFlowViewModel
import com.github.bassaer.chatmessageview.model.ChatUser
import com.github.bassaer.chatmessageview.model.Message
import kotlinx.android.synthetic.main.activity_abuse_info.*

@Suppress("DEPRECATION")
class AbuseInfoActivity : AppCompatActivity() {
    val SessionId = (0..999999999999).random().toString()

    private val viewModel: DialogFlowViewModel by lazy {
        ViewModelProvider(this).get(DialogFlowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abuse_info)


        val human = ChatUser(2, getString(R.string.you), BitmapFactory.decodeResource(resources, R.drawable.ic_account_circle))

        val agent = ChatUser(1, "WomenSafe's Bot", BitmapFactory.decodeResource(resources, R.drawable.bot_profile))

        my_chat_view.setInputTextHint(getString(R.string.chat_start))
        my_chat_view.setRightBubbleColor(getColor(R.color.roxo))
        my_chat_view.setBackgroundDrawable(getDrawable(R.drawable.pagina_login))
        my_chat_view.setUsernameTextColor(Color.WHITE);
        my_chat_view.setSendTimeTextColor(Color.WHITE);
        my_chat_view.setDateSeparatorColor(Color.WHITE);
        my_chat_view.setMessageMaxWidth(700)

        my_chat_view.setOnClickSendButtonListener(
            View.OnClickListener {
                my_chat_view.send(Message.Builder().setUser(human).setText(my_chat_view.inputText).build())
                val text = my_chat_view.inputText
                my_chat_view.inputText =""
                my_chat_view.setInputTextHint(getString(R.string.mensagem))

                viewModel.sendTextMessage(text, SessionId) { response ->
                    if(response.isNullOrEmpty()){
                    Toast.makeText(this,getString(R.string.conection_error), Toast.LENGTH_LONG).show()
                    }
                    else {
                        my_chat_view.send(Message.Builder().setRight(true).setUser(agent).setText(response).build()
                        )
                    }
                }
            }
        )
    }
}


