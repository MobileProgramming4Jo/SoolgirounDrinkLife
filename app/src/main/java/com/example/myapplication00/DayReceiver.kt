package com.example.myapplication00

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.NotificationCompat
import com.example.projectapp.DBHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate


class DayReceiver : BroadcastReceiver() {

    val scope = CoroutineScope(Dispatchers.IO)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

        val routine = goAsync()
        scope.apply {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val channelID = "MyChannelDay"
                val channelName = "Day"
                val notificationChannel = NotificationChannel(channelID, channelName,
                    NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.enableVibration(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE


                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannel)
                val drawable = getDrawable(context, R.drawable.appimg)
                val bitmapDrawable = drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val builder = NotificationCompat.Builder(context, channelID)
                    .setContentTitle("하루 요약")
                    .setContentText("오늘 하루 목표치 달성을 확인하시고 후기를 작성해주세요!")
                    .setSmallIcon(R.drawable.ic_baseline_wine_bar_24)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                manager.createNotificationChannel(notificationChannel)

                val notification = builder.build()
                manager.notify(5, notification)
                Log.v("alarm", "day 알람 발생")

            }

            val alarmManagerDay = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val receiverIntent = Intent(context, DayReceiver::class.java)
            val alarmIntent = PendingIntent.getBroadcast(
                context,
                1,
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val calendar = java.util.Calendar.getInstance().apply {

                timeInMillis = System.currentTimeMillis()
                add(java.util.Calendar.DATE, 1)
            }


            if (Build.VERSION.SDK_INT >= 23) {
                alarmManagerDay.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
            }else {
                if(Build.VERSION.SDK_INT >= 19){
                    alarmManagerDay.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
                }else{
                    alarmManagerDay.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
                }
            }

            val myDBHelper = DBHelper(context);
            val now = LocalDate.now()
            if(!myDBHelper.checkData(now.toString())){
                myDBHelper.insert()
            }


        }
        routine.finish()

    }
}