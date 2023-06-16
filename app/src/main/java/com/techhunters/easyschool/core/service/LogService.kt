package com.techhunters.easyschool.core.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}