package com.techhunters.easyschool.features.tasks.domain.usecase

data class TasksUseCases(
    val getTasks: GetTasks,
    val getTasksUser: GetTasksUser,
    val getTask: GetTask,
    val saveTask: SaveTask,
    val updateTask: UpdateTask,
    val deleteTask: DeleteTask
)