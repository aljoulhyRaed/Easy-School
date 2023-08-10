package com.techhunters.easyschool.core.service.oneSignal

import com.techhunters.easyschool.core.Constants
import com.techhunters.easyschool.core.service.models.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OneSignalService {
    @Headers("Authorization: Bearer ${Constants.ONESIGNAL_API_KEY}")
    @POST("notifications")
    suspend fun sendNotification(@Body notification: Notification): Response<Unit>
}