package com.techhunters.easyschool

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.core.ui.snackbar.SnackBarManager
import com.techhunters.easyschool.core.ui.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class EasySchoolViewModel(private val logService: LogService) : ViewModel()  {
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}