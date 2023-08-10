package com.techhunters.easyschool

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.onesignal.OSSubscriptionObserver
import com.onesignal.OSSubscriptionStateChanges
import com.onesignal.OneSignal
import com.techhunters.easyschool.core.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), OSSubscriptionObserver {
    //private val viewModel:ConversationViewModel by viewModels()
    //private lateinit var mainViewModel: MainViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
       // WindowCompat.setDecorFitsSystemWindows(window, false)
        //mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.ONESIGNAL_APP_ID)

        // OneSignal Enable Notification
        OneSignal.addSubscriptionObserver(this)
        OneSignal.disablePush(false)

        setContent { EasySchoolApp() }
    }
    override fun onOSSubscriptionChanged(p0: OSSubscriptionStateChanges?) {
        if (p0!!.from.isSubscribed &&
            !p0.to.isSubscribed
        ) {
            println("Notifications Disabled!")
        }
        if (!p0.from.isSubscribed &&
            p0.to.isSubscribed
        ) {
            println("Notifications Enabled!")
        }
    }

    /*override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        mainViewModel.setUserStatus(UserStatus.ONLINE)
        super.onResume()
    }

    override fun onPause() {
        mainViewModel.setUserStatus(UserStatus.OFFLINE)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        mainViewModel.setUserStatus(UserStatus.OFFLINE)
        super.onDestroy()
    }*/

}
