package com.techhunters.easyschool.core.service.module

import com.techhunters.easyschool.core.service.ConfigurationService
import com.techhunters.easyschool.core.service.LogService
import com.techhunters.easyschool.core.service.impl.ConfigurationServiceImpl
import com.techhunters.easyschool.core.service.impl.LogServiceImpl
import com.techhunters.easyschool.features.auth.data.AccountRepositoryImpl
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.chat.data.ChatRepositoryImpl
import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository
import com.techhunters.easyschool.features.grades.data.GradesRepositoryImpl
import com.techhunters.easyschool.features.grades.domain.repository.GradesRepository
import com.techhunters.easyschool.features.subjects.data.SubjectsRepositoryImpl
import com.techhunters.easyschool.features.subjects.domain.repository.SubjectsRepository
import com.techhunters.easyschool.features.tasks.data.TasksRepositoryImpl
import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun provideChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    abstract fun  provideTasksRepository(impl: TasksRepositoryImpl): TasksRepository

    @Binds
    abstract fun provideGradesRepository(impl: GradesRepositoryImpl): GradesRepository

    @Binds
    abstract fun provideSubjectsRepository(impl: SubjectsRepositoryImpl): SubjectsRepository

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService


}