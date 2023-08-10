package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class GetTask(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(
        gradeId: String,
        subId: String,
        taskId: String
    ) = tasksRepository.getTask(gradeId, subId, taskId)
}