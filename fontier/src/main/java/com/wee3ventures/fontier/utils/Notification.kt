package com.wee3ventures.fontier.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat

class Notification (private val mContext: Context) {
    private var notificationManager : NotificationManager? = null
    private var summaryNotificationBuilder: NotificationCompat.Builder? = null
    private var singleNotificationId : Int = 100
    private var bundleNotificationId : Int = 100
    companion object {
        const val PROGRESS_TAG = "progress_tag"
        const val ERROR_TAG = "error_tag"
        const val PROGRESS_ID = 1056
        const val ERROR_ID = 1231
    }
    init {
        notificationManager = mContext.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
    }

    fun createChannel(channelId : String, channelName : String, importance : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,channelName,importance))
        }
    }

    fun createChannels(channelList : List<NotificationChannel>){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.createNotificationChannels(channelList)
        }
    }

    fun createChannelGroups(groupList : List<NotificationChannelGroup>){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.createNotificationChannelGroups(groupList)
        }
    }

    fun createChannelGroup(groupId : String, groupName : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.createNotificationChannelGroup(NotificationChannelGroup(groupId,groupName))
        }
    }

    fun showNotification(title : String, message : String,notifyId: Int,@Nullable autoCancel : Boolean = true, smallIcon : Int){
        val notification = NotificationCompat.Builder(mContext)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(autoCancel)
            .setSmallIcon(smallIcon)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        notificationManager?.notify(notifyId, notification.build())
    }

    fun showNotification(title : String, message : String, summaryChannelId : String, notificationChannelId : String){
        val bundle_notification_id = "bundle_notification_$bundleNotificationId"
        summaryNotificationBuilder = NotificationCompat.Builder(mContext, summaryChannelId)
            .setGroup(bundle_notification_id)
            .setGroupSummary(true)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        if (singleNotificationId == bundleNotificationId)
            singleNotificationId = System.currentTimeMillis().toInt()
        else
            singleNotificationId ++

        val notification = NotificationCompat.Builder(mContext, notificationChannelId)
            .setGroup(bundle_notification_id)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setGroupSummary(false)
            .setAutoCancel(true)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)

        notificationManager?.notify(singleNotificationId, notification.build())
        notificationManager?.notify(bundleNotificationId, summaryNotificationBuilder!!.build())

    }

    @Suppress("SENSELESS_COMPARISON")
    fun showProgressNotification(title : String, message : String, @Nullable progress : Int? = null, progressChannelId : String){
        cancelNotification()
        val notification = NotificationCompat.Builder(mContext, progressChannelId)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setGroupSummary(false)
            .setAutoCancel(true)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        if (progress != 0 || progress != null){
            notification.setProgress(100,progress!!,false)
        } else {
            notification.setProgress(0, 0,true)
        }
        notificationManager?.notify(PROGRESS_TAG, PROGRESS_ID, notification.build())
    }

    fun showError(title : String, message : String, smallIcon: Int,@Nullable errorChannelId : String ?= null,errorColor : Int){
        cancelNotification()
        val notification = if (errorChannelId.isNullOrEmpty().not()){
            NotificationCompat.Builder(mContext, "$errorChannelId")
        } else {
            NotificationCompat.Builder(mContext)
        }
            .setContentTitle(title)
            .setContentText(message)
            .setColorized(true)
            .setColor(errorColor)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setGroupSummary(false)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setSmallIcon(smallIcon)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        notificationManager?.notify(ERROR_TAG, ERROR_ID, notification.build())
    }

    fun cancelNotification(){
        notificationManager?.cancelAll()
    }

    fun cancelNotification(notifyId : Int){
        notificationManager?.cancel(notifyId)
    }

    fun cancelNotification(tag : String, notifyId : Int){
        notificationManager?.cancel(tag, notifyId)
    }
}