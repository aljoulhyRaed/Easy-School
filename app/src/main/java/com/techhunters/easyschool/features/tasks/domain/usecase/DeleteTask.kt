package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class DeleteTask(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(
        gradeId: String,
        subId: String,
        taskId: String
    ) = tasksRepository.deleteTask(gradeId, subId, taskId)
}