package com.techhunters.easyschool.core.service.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.techhunters.easyschool.core.service.oneSignal.OneSignalService
import com.techhunters.easyschool.features.auth.domain.repository.AccountRepository
import com.techhunters.easyschool.features.auth.domain.usecase.AuthUseCases
import com.techhunters.easyschool.features.auth.domain.usecase.GetCurrentUserId
import com.techhunters.easyschool.features.auth.domain.usecase.GetCurrentUser
import com.techhunters.easyschool.features.auth.domain.usecase.GetUserProfileData
import com.techhunters.easyschool.features.auth.domain.usecase.GetUserRole
import com.techhunters.easyschool.features.auth.domain.usecase.HasUser
import com.techhunters.easyschool.features.auth.domain.usecase.LoginWithEmailAndPassword
import com.techhunters.easyschool.features.auth.domain.usecase.SendRecoveryEmail
import com.techhunters.easyschool.features.auth.domain.usecase.SetUserStatus
import com.techhunters.easyschool.features.auth.domain.usecase.SignOut
import com.techhunters.easyschool.features.chat.domain.repository.ChatRepository
import com.techhunters.easyschool.features.chat.domain.usecase.ChatUseCases
import com.techhunters.easyschool.features.chat.domain.usecase.GetAuthorImageUrl
import com.techhunters.easyschool.features.chat.domain.usecase.GetChatUserId
import com.techhunters.easyschool.features.chat.domain.usecase.GetGradeStudents
import com.techhunters.easyschool.features.chat.domain.usecase.InsertMessage
import com.techhunters.easyschool.features.chat.domain.usecase.LoadMessages
import com.techhunters.easyschool.features.chat.domain.usecase.SendNotification
import com.techhunters.easyschool.features.grades.domain.repository.GradesRepository
import com.techhunters.easyschool.features.grades.domain.usecase.GradesUseCases
import com.techhunters.easyschool.features.grades.domain.usecase.GetGrades
import com.techhunters.easyschool.features.subjects.domain.repository.SubjectsRepository
import com.techhunters.easyschool.features.subjects.domain.usecase.GetSubjects
import com.techhunters.easyschool.features.subjects.domain.usecase.SubjectsUseCases
import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository
import com.techhunters.easyschool.features.tasks.domain.usecase.DeleteTask
import com.techhunters.easyschool.features.tasks.domain.usecase.GetTask
import com.techhunters.easyschool.features.tasks.domain.usecase.GetTasks
import com.techhunters.easyschool.features.tasks.domain.usecase.GetTasksUser
import com.techhunters.easyschool.features.tasks.domain.usecase.SaveTask
import com.techhunters.easyschool.features.tasks.domain.usecase.TasksUseCases
import com.techhunters.easyschool.features.tasks.domain.usecase.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    fun fireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun fireStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideOneSignalService(): OneSignalService {
        return Retrofit.Builder()
            .baseUrl("https://onesignal.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OneSignalService::class.java)
    }

    @Provides
    fun provideAuthUseCases(accountRepository: AccountRepository) = AuthUseCases(
        getCurrentUser = GetCurrentUser(accountRepository),
        getCurrentUserId = GetCurrentUserId(accountRepository),
        hasUser = HasUser(accountRepository),
        loginWithEmailAndPassword = LoginWithEmailAndPassword(accountRepository),
        sendRecoveryEmail = SendRecoveryEmail(accountRepository),
        getUserProfileData= GetUserProfileData(accountRepository),
        setUserStatus = SetUserStatus(accountRepository),
        getUserRole = GetUserRole(accountRepository),
        signOut = SignOut(accountRepository)
    )

    @Provides
    fun provideChatUseCases(chatRepository: ChatRepository) = ChatUseCases(
        insertMessage = InsertMessage(chatRepository),
        loadMessages = LoadMessages(chatRepository),
        getAuthorImageUrl = GetAuthorImageUrl(chatRepository),
        getChatUserId = GetChatUserId(chatRepository),
        getGradeStudents = GetGradeStudents(chatRepository),
        sendNotification = SendNotification(chatRepository)
    )

    @Provides
    fun provideTasksUseCases(tasksRepository: TasksRepository) = TasksUseCases(
        getTasks = GetTasks(tasksRepository),
        getTask = GetTask(tasksRepository),
        saveTask = SaveTask(tasksRepository),
        updateTask = UpdateTask(tasksRepository),
        deleteTask = DeleteTask(tasksRepository),
        getTasksUser = GetTasksUser(tasksRepository),
    )

    @Provides
    fun provideGradesUseCases(gradesRepository: GradesRepository) = GradesUseCases(
        getGrades = GetGrades(gradesRepository),

    )

    @Provides
    fun provideSubjectsUseCases(subjectsRepository: SubjectsRepository) = SubjectsUseCases(
        getSubjects = GetSubjects(subjectsRepository)
    )

}