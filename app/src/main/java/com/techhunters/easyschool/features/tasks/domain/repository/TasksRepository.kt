package com.techhunters.easyschool.features.tasks.domain.repository

import com.techhunters.easyschool.features.auth.domain.models.User
import com.techhunters.easyschool.features.tasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(gradeId: String, subId: String): Flow<List<Task>>

    suspend fun getTasksUser(): User?
    suspend fun getTask(gradeId: String, subId: String, taskId: String): Task?

    suspend fun saveTask(gradeId: String, subId: String, task: Task): String

    suspend fun updateTask(gradeId: String, subId: String, task: Task)

    suspend fun deleteTask(gradeId: String, subId: String, taskId: String)
}