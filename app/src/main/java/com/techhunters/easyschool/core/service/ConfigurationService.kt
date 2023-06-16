package com.techhunters.easyschool.core.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
    val isShowTaskEditButtonConfig: Boolean
}