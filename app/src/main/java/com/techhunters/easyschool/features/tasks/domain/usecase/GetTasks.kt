package com.techhunters.easyschool.features.tasks.domain.usecase

import com.techhunters.easyschool.features.tasks.domain.repository.TasksRepository

class GetTasks(private val tasksRepository: TasksRepository) {
    operator fun invoke(gradeId: String, subId: String) =
        tasksRepository.getTasks(gradeId, subId)
}