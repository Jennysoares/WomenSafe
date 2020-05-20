package br.com.victoriasantos.womensafe.view.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.util.Log
import br.com.victoriasantos.womensafe.R

class ProximityIntentReceiver : BroadcastReceiver() {

    private lateinit var builder : Notification.Builder

    override fun onReceive(context: Context,  intent: Intent) {
         val key = LocationManager.KEY_PROXIMITY_ENTERING;
         val entering = intent.getBooleanExtra(key, false);
         if (entering) {
             Log.d(javaClass.simpleName, "entering");

         } else {
             Log.d(javaClass.simpleName, "exiting");
         }

         val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MapsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              val channelId = "${context.packageName}"
              val notificationChannel = NotificationChannel(channelId, "WomenSafe", NotificationManager.IMPORTANCE_HIGH)
              notificationChannel.enableLights(true)
              notificationChannel.lightColor = Color.RED
              notificationChannel.enableVibration(true)
              notificationManager.createNotificationChannel(notificationChannel)

              builder = Notification.Builder(context,channelId).apply {
                 setSmallIcon(R.drawable.logo)
                  setContentTitle("WomenSafe")
                  setContentText("Você está próxima a um local marcado como perigoso por nossos usuários. Clique para verificar o mapa.")
                  setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
                  setContentIntent(pendingIntent)
                  setAutoCancel(true)
             }
          } else{

               builder = Notification.Builder(context).apply {
                   setContentTitle("WomenSafe")
                   setContentText("Você está próxima a um local marcado como perigoso por nossos usuários. Clique para verificar o mapa.")
                   setSmallIcon(R.drawable.logo)
                   setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
                   setContentIntent(pendingIntent)

              }
          }

          notificationManager.notify(intent.getIntExtra("int", 0), builder.build())

        }

    }