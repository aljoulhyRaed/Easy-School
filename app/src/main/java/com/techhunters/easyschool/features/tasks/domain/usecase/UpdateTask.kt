package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class UpdateTask(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(
        gradeId: String,
        subId: String,
        task: Task
    ) = tasksRepository.updateTask(gradeId, subId, task)
}