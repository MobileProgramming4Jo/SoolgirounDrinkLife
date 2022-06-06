package com.example.myapplication00

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onReceive(context: Context, intent: Intent) {
        val routine = goAsync()
        scope.apply {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelID = "MyChannelWeek"
                val channelName = "week"
                val notificationChannel = NotificationChannel(channelID, channelName,
                    NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.enableVibration(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE


                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannel)
                val drawable = AppCompatResources.getDrawable(
                    context,
                    com.example.myapplication00.R.drawable.appimg
                )
                val bitmapDrawable = drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val builder = NotificationCompat.Builder(context, channelID)
                    .setContentTitle("술약속이 있으신가요?")
                    .setContentText("접속하셔서 일정을 기록하고 목표를 달성해보세요!")
                    .setSmallIcon(com.example.myapplication00.R.drawable.ic_baseline_wine_bar_24)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                manager.createNotificationChannel(notificationChannel)

                val notification = builder.build()
                manager.notify(10, notification)
                Log.v("alarm", "Week 알람 발생")
            }
            val alarmManagerWeek = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val receiverIntent = Intent(context, AlarmReceiver::class.java)
            val alarmIntent = PendingIntent.getBroadcast(
                context,
                7,
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val calendar = java.util.Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                add(Calendar.DATE, 7)
            }
            if (Build.VERSION.SDK_INT >= 23) {
                alarmManagerWeek.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
            }else {
                if(Build.VERSION.SDK_INT >= 19){
                    alarmManagerWeek.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
                }else{
                    alarmManagerWeek.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
                }
            }
        }
        routine.finish()
    }
}