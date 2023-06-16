package com.techhunters.easyschool.core.service

import com.techhunters.easyschool.features.teacher.tasks.domain.Task
import kotlinx.coroutines.flow.Flow

interface DataStorageService {
    val tasks: Flow<List<Task>>

    suspend fun getTask(taskId: String): Task?

    suspend fun save(task: Task): String
    suspend fun update(task: Task)
    suspend fun delete(taskId: String)
}