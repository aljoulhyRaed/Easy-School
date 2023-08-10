package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.model.Task
import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class SaveTask(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(
        gradeId: String,
        subId: String,
        task: Task,
    ) = tasksRepository.saveTask(gradeId, subId, task)
}