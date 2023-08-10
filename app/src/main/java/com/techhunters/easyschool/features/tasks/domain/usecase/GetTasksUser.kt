package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class GetTasksUser(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke()= tasksRepository.getTasksUser()
}