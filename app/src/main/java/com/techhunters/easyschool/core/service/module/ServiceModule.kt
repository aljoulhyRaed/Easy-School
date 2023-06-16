package com.techhunters.easyschool.core.service.module

import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.DataStorageService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.core.service.impl.CloudFireStoreServiceImpl
import com.techhunters.easyschool.core.service.impl.ConfigurationServiceImpl
import com.techhunters.easyschool.core.service.impl.LogServiceImpl
import com.techhunters.easyschool.features.auth.data.AccountServiceImpl
import com.techhunters.easyschool.features.auth.domain.AccountService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideStorageService(impl: CloudFireStoreServiceImpl): DataStorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}